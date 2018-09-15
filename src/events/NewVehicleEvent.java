package events;

import java.util.List;

import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.GenericJunction;
import model.objects.Vehicle;
import utils.MapObjectsParser;

public class NewVehicleEvent extends Event {
	
	protected String id;
	protected Integer maxSpeed;
	protected String[] itinerary;

	public NewVehicleEvent(int time, String id, int vMax,
							   String[] iti) {
		super(time);
		this.id = id;
		this.maxSpeed = vMax;
		this.itinerary = iti;
	}

	@Override 
	public void execute(RoadMap map) throws SimulationError {
		// Parse the junction list ( itinerary ) of each vehicle
		List<GenericJunction<?>> iti = MapObjectsParser.parseJunctionList(itinerary, map);
		
		// Itinerary must have at least two junctions
		if(iti == null || iti.size() < 2)
			throw new SimulationError("Invalid itinerary for vehicle: " + id);
		else {
			Vehicle v = createsVehicle(iti);
			map.addVehicle(id, v);
		}
	}
	
	protected Vehicle createsVehicle(List<GenericJunction<?>> iti)throws SimulationError {
		return new Vehicle(this.id, this.maxSpeed, iti);
	}
	
	public String  toString() {
		return "New Vehicle  " + id;
	}
}
