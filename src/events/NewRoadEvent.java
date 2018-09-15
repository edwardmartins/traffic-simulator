package events;

import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.Road;
import model.objects.GenericJunction;

public class NewRoadEvent extends Event {

	protected String id;
	protected Integer maxSpeed;
	protected Integer length;
	protected String originJunctionId;
	protected String destinationJunctionId;

	public NewRoadEvent(int time, String id, String origin,
								String destination, int vMax,int len){
		super(time);
		this.id = id;
		this.maxSpeed = vMax;
		this.length = len;
		originJunctionId = origin;
		destinationJunctionId = destination;
	}

	@Override
	public void execute(RoadMap map) throws SimulationError {
		// Obtain origin and destination Junction
		GenericJunction<?> origin = map.getJunction(originJunctionId);
		GenericJunction<?> destination = map.getJunction(destinationJunctionId);
		// Creates and adds road to the map
		Road road = createRoad(origin, destination);
		map.addRoad(id, origin, road, destination);
	}
	
	protected Road createRoad(GenericJunction<?> originJunction, GenericJunction<?> destinationJunction) {
			 return new Road(this.id, this.length, this.maxSpeed,
					              originJunction, destinationJunction);	 
	}
	
	public String  toString() {
		return "New Road  " + id;
	}

}
