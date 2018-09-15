package events;


import java.util.List;

import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.Vehicle;
import utils.MapObjectsParser;

public class CarBreakDownEvent extends Event{
	
	protected String[] vehicles;
	protected int duration;
	
	public CarBreakDownEvent(int time, String[] vehicles, int duration) {
		super(time);
		this.vehicles = vehicles;
		this.duration = duration;
	}

	@Override
	public void execute(RoadMap map) throws SimulationError {
		// Parse list of vehicles
		List<Vehicle> damagedVehicles = MapObjectsParser.parseVehiclesList(vehicles, map);
		
		if(damagedVehicles == null) {
			throw new SimulationError("Invalid list of vehicles for make_vehicle_faulty");
		}
		else {
			// Set the time of breakdown for each vehicle in the list
			for(Vehicle v : damagedVehicles) {
				v.setBreakdownDuration(duration);
			}
		}	
	}
	
	public String toString() {
		String s = "Damaged Vehicles [";
		for(int i = 0; i < vehicles.length; i++) {
			s += vehicles[i];
						  
			if(!(i == vehicles.length - 1))
					s += ",";
		}
		s += "]";									
		return s;	
	}
}
