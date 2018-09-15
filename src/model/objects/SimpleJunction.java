package model.objects;

public class SimpleJunction extends GenericJunction<EntryRoad>{

	public SimpleJunction(String id) {
		super(id);
	}

	@Override
	protected EntryRoad createEntryRoad(Road road) {
		return new EntryRoad(road);
	}

	@Override
	protected void updateTrafficLights() {
		
		// There is no road with a green light
		if(greenLightIndex == -1) {
			greenLightIndex = 0;
			putGreenLight(greenLightIndex);
		}
		else {
			
			// Put the current entryroad with a greenlight to red
			putRedLight(greenLightIndex);
			
			// Find next entry road
			greenLightIndex++;
			greenLightIndex = greenLightIndex % entryRoadsList.size();
		
			// Put the next entryRoad trafficlight green
			putGreenLight(greenLightIndex);
		}
		
	}

}
