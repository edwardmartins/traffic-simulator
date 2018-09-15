package model.objects;

import java.util.ArrayList;
import java.util.List;

import exceptions.SimulationError;


public class EntryRoad {

	protected Road road;
	protected List<Vehicle> vehiclesQueue; 
	protected boolean trafficLight; // true=green, false=red

	public EntryRoad(Road road) {
		this.road = road;	
		vehiclesQueue = new ArrayList<Vehicle>();
		trafficLight = false;
	}

	// true=green, false=red
	public void putTrafficLight(boolean color) {
		trafficLight = color;
	}
	
	
	public int getVehicleQueueSize() {
		return vehiclesQueue.size();
	}

	// Remove the first vehicle of the queue and move it to its next road
	public void advanceFirstVehicle() throws SimulationError{
		if(!vehiclesQueue.isEmpty()) {
			Vehicle vehicle = vehiclesQueue.remove(0);
			vehicle.moveToNextRoad();
		}
	}
	
	
	public Road getRoad() {
		return road;
	}
	
	
	public boolean hasGreenLight() {
		return trafficLight;
	}
	
	
	public void addToVehiclesQueue(Vehicle v) {
		vehiclesQueue.add(v);	
	}

	@Override
	public String toString() {
		String result = "";
	
		result = "(" + road +"," + (trafficLight ? "green" : "red") + ",[" ;
		
		for(int i = 0; i < vehiclesQueue.size(); i++) {
			result += vehiclesQueue.get(i);
			
			if(i != vehiclesQueue.size() -1)
				result += ",";
		}
		result += "])";
		return result;			  
	}
}
