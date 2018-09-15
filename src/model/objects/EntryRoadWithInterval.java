package model.objects;

import exceptions.SimulationError;

public class EntryRoadWithInterval extends EntryRoad {
	
	
	private int timeInterval; // time until the trafficlight gets red
	private int usedTimeUnits; // each time we advance a vehicle we incrementthe time
	private boolean completeUse; // true when each time that we have had a green light a vehicle has passed
	private boolean oneVehicleUse; // true when one vehicle has passed with a green light
	

	public EntryRoadWithInterval(Road road, int interval) {
		super(road);
		timeInterval = interval;
		usedTimeUnits = 0;
		completeUse = true;
		oneVehicleUse = false; 
	}


	@Override
	public void advanceFirstVehicle() throws SimulationError { 
	
		usedTimeUnits++;
	
		if(vehiclesQueue.isEmpty())
			completeUse = false;
		else {
			Vehicle vehicle = vehiclesQueue.remove(0);
			vehicle.moveToNextRoad();
			oneVehicleUse = true; 
		}
	}
	
	
	public boolean isTimeConsumed() {
		return (usedTimeUnits >= timeInterval);
	}
	
	
	public void setTimeInterval(int inter) {
		timeInterval = inter;
	}
	
	
	public void setUsedTimeUnits(int used) {
		usedTimeUnits = used;
	}
	
	
	public int getRemainingTime() {
		return (timeInterval - usedTimeUnits);
	}
	
	
	public void setCompleteUse(boolean val) {
		completeUse = val;
	}
	
	
	public void setOneVehicleUse(boolean val) {
		oneVehicleUse = val;
	}
	
	public int getTimeInterval(){ return timeInterval; }
	
	public boolean getCompleteUse() { return completeUse; } 
	
	public boolean getOneVehicleUse() { return oneVehicleUse; } 
	
	
	public String toString() {
		String result = "";
	
		result = "(" + road +"," + 
		(trafficLight ? "green:" + getRemainingTime() : "red") + ",[" ;
		
		for(int i = 0; i < vehiclesQueue.size(); i++) {
			result += vehiclesQueue.get(i);
			
			if(i != vehiclesQueue.size() -1)
				result += ",";
		}
		result += "])";
		return result;			  
	}
	
	
}
