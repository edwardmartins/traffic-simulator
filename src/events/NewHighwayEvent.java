package events;

import model.objects.Highway;
import model.objects.Road;
import model.objects.GenericJunction;

public class NewHighwayEvent extends NewRoadEvent {
	
	protected Integer lanes;

	public NewHighwayEvent(int time, String id, String origin, String destination,
			int vMax, int len, Integer numLanes) {
		
		super(time, id, origin, destination, vMax, len);
		lanes = numLanes;
	}

	@Override
	protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction) {
		return new Highway(this.id, this.length, this.maxSpeed,
					              originJunction, destinationJunction, lanes);
	}

}
