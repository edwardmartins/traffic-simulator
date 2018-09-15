package utils;

import java.util.ArrayList;
import java.util.List;

import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.GenericJunction;
import model.objects.Vehicle;

public class MapObjectsParser {

	public static List<GenericJunction<?>> parseJunctionList(String[] itinerary,
			RoadMap map) throws SimulationError {
		
		List <GenericJunction<?>> iti = new ArrayList<GenericJunction<?>>();
		
		for(int i = 0; i < itinerary.length; i++) {
			iti.add(map.getJunction(itinerary[i])); 
		}
		return iti;
	}
	
	public static List <Vehicle> parseVehiclesList(String[] vehicls,
			RoadMap map) throws SimulationError{
		
		List <Vehicle> vehicles = new ArrayList<Vehicle>();
		
		for(int i = 0; i < vehicls.length; i++) {
			vehicles.add(map.getVehicle(vehicls[i]));
		}
		return vehicles;	
	}
}
