package model.objects;

import java.util.List;

import exceptions.SimulationError;
import ini.IniSection;

public class Vehicle extends SimulationObject {
	
	 protected Road road; 
	 protected int maxVelocity; 
	 protected int currentSpeed;
	 protected int kilometrage; 
	 protected int location; 
	 protected int breakDownDuration; 
	 protected List<GenericJunction<?>> itinerary; // number of junctions the vehicle has to pass
	 protected boolean hasArrived; // tell us if the vehicle has arrived to the destination
	 protected boolean inTheJunction; // tell us if the vehicle is in a junction
	 protected int junctionCount; // tell us how many junctions the vehicle has passed

	 
	public Vehicle(String id, int maxSpeed, List <GenericJunction<?>> iti) throws SimulationError {
		super(id);
		
		if(maxVelocity >= 0 && iti.size() >= 2) {
			road = null;
			maxVelocity = maxSpeed;
			currentSpeed = 0;
			kilometrage = 0;
			location = 0;
			breakDownDuration = 0;
			itinerary = iti;
			hasArrived = false;
			inTheJunction = false;
			junctionCount = 0;
		}
		else
			throw new SimulationError(" wrong parameters for vehicle" + id); 
	}

	@Override
	public void advance() {
		// Test if the vehicle has a breakdown
		if(breakDownDuration > 0) {
			breakDownDuration--;
		}
		else if(!inTheJunction) { 
			
			// Update location and kilometrage
			location += currentSpeed;
			kilometrage += currentSpeed;
			
			// If the vehicle arrives to a junction
			if(location >= road.getRoadLength()) {
				
				// We correct kilometrage and location
				kilometrage = kilometrage - location + road.getRoadLength();
				location = road.getRoadLength();
				
				// The vehicle will enter to the queue of the junction
				road.vehicleArrivesToJunction(this);
				currentSpeed = 0;
				inTheJunction = true;
			}
		}
	}
	
	
	public void moveToNextRoad() throws SimulationError {
		
		// Road is null at the beggining of the simulation
		if(road != null) 
			road.removeVehicle(this);
		
		// If we arrived at the last junction of the itinerary
		if(junctionCount + 1 == itinerary.size()) {
			hasArrived = true;
			road = null;
			currentSpeed = 0;
			location = 0;
		}
		else{ // In other case we move the vehicle to his next road
			
			Road nextRoad = itinerary.get(junctionCount).getRoadToJunction(itinerary.get(junctionCount + 1));
			
			if(nextRoad != null) {
				// Update the values for the next road
				location = 0;
				nextRoad.enterVehicle(this); 
				road = nextRoad; 
				inTheJunction = false;
				junctionCount++;
			}
			else
				throw new SimulationError("inexistent road");		
		}	
	}
	

	public void setCurrentSpeed(int speed) {
		if(speed < 0)
			currentSpeed = 0;
		else if(speed > maxVelocity)
			currentSpeed = maxVelocity;
		else
			currentSpeed = speed;
	}
	
	public void setBreakdownDuration(Integer duration) {
		if(road != null) {
			breakDownDuration += duration;
			
			if(breakDownDuration > 0) {
				currentSpeed = 0;
			}
		}
	}
	
	@Override
	protected void completeSectionDetails(IniSection is) {
	is.setValue("speed", currentSpeed);
	is.setValue("kilometrage", kilometrage);
	is.setValue("faulty", breakDownDuration);
	is.setValue("location", hasArrived ? "arrived" :
		"(" + road + "," + getLocation() + ")");
	}
	
	public int getLocation() {
		return location;	
	}
	
	public int getBreakDownDuration() {
		return breakDownDuration;
	}
	
	public boolean getInTheJunction(){
		return inTheJunction;
	}
	
	public Road getRoad() {
		return road;
	}
	
	public int getCurrentSpeed() {
		return currentSpeed;
	}

	public int getKilometrage() {
		return kilometrage;
	}

	public List<GenericJunction<?>> getItinerary() {
		return itinerary;
	}
	
	@Override
	protected String getSectionName() {
		return "vehicle_report";
	}
}
