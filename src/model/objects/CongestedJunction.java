package model.objects;

import ini.IniSection;

public class CongestedJunction extends GenericJunction<EntryRoadWithInterval> {

	static final int INITIAL_INTERVAL = 0;
	
	public CongestedJunction(String id) {
		super(id);
	}

	@Override
	protected EntryRoadWithInterval createEntryRoad(Road road) {
		return new EntryRoadWithInterval(road,INITIAL_INTERVAL);
	}

	@Override
	protected void updateTrafficLights() {
		int max; 
		// There is no road with a green light
		if (greenLightIndex == -1) {
			
			// Search the entry road with most vehicles
			max = findEntryRoadWithMostVehicles(greenLightIndex);
			greenLightIndex = max;
			putGreenLight(greenLightIndex);
			
			// Set the interval
			EntryRoadWithInterval riInicial = getEntryRoadWithGreenLight();
			riInicial.setTimeInterval(Math.max(riInicial.getVehicleQueueSize() / 2, 1));

		} else {
			EntryRoadWithInterval ri = getEntryRoadWithGreenLight();

			if (ri.isTimeConsumed()) {

				// Put the current entryroad with a greenlight to red
				putRedLight(greenLightIndex);
				ri.setUsedTimeUnits(0);

				// Search the entry road with most vehicles
				max = findEntryRoadWithMostVehicles(greenLightIndex);
				greenLightIndex = max;
				putGreenLight(greenLightIndex);
				
				// Set the interval
				EntryRoadWithInterval riSig = getEntryRoadWithGreenLight();
				riSig.setTimeInterval(Math.max(riSig.getVehicleQueueSize() / 2, 1));
			}
		}
	}

	// Search the entry road with most vehicles in its queue
	// it has to be different than the current one
	private int findEntryRoadWithMostVehicles(int currentIndex) {
		int max = -1;
		
		if(entryRoadsList.size() == 1)
			max = 0;
		else {
			
			for (int i = 0; i < entryRoadsList.size(); i++) {
				if (entryRoadsList.get(i).getVehicleQueueSize() > max && currentIndex != i) {
					max = i;
				}
			}
		}
		return max;
	}
	
	@Override
	protected void completeSectionDetails(IniSection is) {
		super.completeSectionDetails(is);
		is.setValue("type","mc");
	}
}
