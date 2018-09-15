package model.objects;

import ini.IniSection;

public class CircularJunction extends GenericJunction<EntryRoadWithInterval> {

	protected int intervalMinValue;
	protected int intervalMaxValue;

	public CircularJunction(String id, int min, int max) {
		super(id);
		intervalMinValue = min;
		intervalMaxValue = max;
	}

	@Override
	protected EntryRoadWithInterval createEntryRoad(Road road) {
		return new EntryRoadWithInterval(road, intervalMaxValue);
	}

	@Override
	protected void updateTrafficLights() {
		
		// There is no road with a green light
		if (greenLightIndex == -1) { 
			greenLightIndex = 0;
			putGreenLight(greenLightIndex);
			
		} else {
			
			EntryRoadWithInterval ri = getEntryRoadWithGreenLight();
			
			if (ri.isTimeConsumed()) {
				
				// Put the current entryroad with a greenlight to red
				putRedLight(greenLightIndex);

				// Each time that we have had a green light a vehicle has passed
				if (ri.getCompleteUse()) {
					ri.setTimeInterval(Math.min(ri.getTimeInterval() + 1,
																intervalMaxValue));
				}// No vehicle has passed when we have had a green light
				else if (!ri.getOneVehicleUse()) {
					ri.setTimeInterval(Math.max(ri.getTimeInterval() - 1,
																intervalMinValue));
				}
				
				// Update values
				ri.setUsedTimeUnits(0);
				ri.setOneVehicleUse(false);
				ri.setCompleteUse(true);
				
				// Put the next entryRoad trafficlight green
				greenLightIndex++;
				greenLightIndex = greenLightIndex % entryRoadsList.size();
				putGreenLight(greenLightIndex);
			}
			
		}
	}
	
	@Override
	protected void completeSectionDetails(IniSection is) {
		super.completeSectionDetails(is);
		is.setValue("type","rr");
	}

}
