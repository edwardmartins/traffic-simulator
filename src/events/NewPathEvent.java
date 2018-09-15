package events;

import model.objects.Path;
import model.objects.Road;
import model.objects.GenericJunction;

public class NewPathEvent extends NewRoadEvent {

	public NewPathEvent(int time, String id, String origin, String destination,
							 int vMax, int len) {
		
		super(time, id, origin, destination, vMax, len);
	}

	@Override
	protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction) {
		return new Path(this.id, this.length, this.maxSpeed,
				          originJunction, destinationJunction);
	}
}
