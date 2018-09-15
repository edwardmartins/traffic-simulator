package model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exceptions.SimulationError;
import model.objects.Road;
import model.objects.GenericJunction;
import model.objects.Vehicle;

// Store the simulation objects : roads, vehicles an junctions
// Advance the simulation
public class RoadMap {

	private List<Road> roads;
	private List<GenericJunction<?>> genericJunctions;
	private List<Vehicle> vehicles;
	
	// Maps to agilize searching of the simulation objects 
	private Map<String, Road> roadsIds;
	private Map<String, GenericJunction<?>> junctionsIds;
	private Map<String, Vehicle> vehiclesIds;

	public RoadMap() {
		
		// Lists
		roads = new ArrayList<Road>();
		genericJunctions = new ArrayList<GenericJunction<?>>();
		vehicles = new ArrayList<Vehicle>();
		
		// Maps
		roadsIds = new HashMap<String, Road>();
		junctionsIds = new HashMap<String, GenericJunction<?>>();
		vehiclesIds = new HashMap<String, Vehicle>();

	}

	public void addJunction(String junctionId, GenericJunction<?> genericJunction)
			throws SimulationError {
		
		if(!junctionsIds.containsKey(junctionId)) {
			genericJunctions.add(genericJunction);
			junctionsIds.put(junctionId, genericJunction);
		}
		else 
			throw new SimulationError("Cannot add a junction that already exists ");
		
	}

	public void addVehicle(String vehicleId, Vehicle vehicle) 
			throws SimulationError {
		
		if(!vehiclesIds.containsKey(vehicleId)){
			vehicles.add(vehicle);
			vehiclesIds.put(vehicleId, vehicle);
			
			// Move the vehicle to his beggining road
			vehicle.moveToNextRoad();
		}
		else  
			throw new SimulationError("Cannot add a vehicle that already exists");
	}
	
	public void addRoad(String roadId, GenericJunction<?> origin,
			Road road, GenericJunction<?> destination) throws SimulationError {
		
			if(!roadsIds.containsKey(roadId)) {
				roads.add(road);
				roadsIds.put(roadId, road);
				 
				// Connects the road with his origin and destination Junction
				origin.addOutgoingRoad(destination, road);
				destination.addEntryRoad(roadId, road); 
			 }
			 else
				 throw new SimulationError("Cannot add a road that already exists");
	}
	
	public String generateReport(int time) {
		
		 String report = "";
		 
		 // Junction Inform
		 for(GenericJunction<?> junction : genericJunctions)
			 report += junction.generateInform(time) + "\n";
		 
		 // Roads Inform
		 for(Road road : roads) {
			report +=  road.generateInform(time) + "\n";
		 }
		 // Vehicles Inform
		 for(Vehicle v : vehicles) {
			 report += v.generateInform(time) + "\n";
		 }
		 
		return report;
	}
	
	
	public void update() throws SimulationError {
		
		// Updates the road that internally updates the vehicles
		for(Road road : roads) {
				road.advance();
		}
		
		// Updates the junctions
		for(GenericJunction<?> junction : genericJunctions) {
			junction.advance();
		}
	}

	public GenericJunction<?> getJunction(String id) throws SimulationError {
		GenericJunction<?> junction =  junctionsIds.get(id);
		
		if(junction != null)
			return junction;
		else 
			throw new SimulationError("Cannot add road cause "
					+ "does not exist a junction with id : " + id);
			
	}
	
	public Vehicle getVehicle(String id) throws SimulationError {
		Vehicle vehicle = vehiclesIds.get(id);
		
		if(vehicle != null)
			return vehicle;
		else
			throw new SimulationError("Does not exist vehicle with id : " + id );
	}
	
	public Road getRoad(String id) throws SimulationError {
		Road road = roadsIds.get(id);
		
		if(road != null)
			return road;
		else 
			throw new SimulationError("Does not exist road with id : " + id);
			
	}

	public List<Road> getRoads() {
		return roads;
	}

	public List<GenericJunction<?>> getJunctions() {
		return genericJunctions;
	}
	
	public List<Vehicle> getVehicles() {
			return vehicles;
	}
	
	
}
