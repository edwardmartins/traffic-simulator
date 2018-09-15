package model.objects;

import java.util.Comparator;
import java.util.List;

import ini.IniSection;
import utils.SortedArrayList;

public class Road extends SimulationObject {
	
	protected int length; 
	protected int maxSpeed; 
	protected GenericJunction<?> originJunction;
	protected GenericJunction<?> destinationJunction; 
	protected List<Vehicle> vehicles; 
	protected Comparator<Vehicle> vehicleComparator; 
	
	
	public Road(String id, int len, int maxVelocity, GenericJunction<?> orig, GenericJunction<?> dest) {
		 super(id);
		 length = len;
		 maxSpeed = maxVelocity;
		 originJunction = orig;
		 destinationJunction = dest;
		 
		// Vehicles order according to location
		 vehicleComparator = new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				if(o1.getLocation() > o2.getLocation()) return -1;
				else if(o1.getLocation() < o2.getLocation()) return 1;
				else return 0;
			}	 	 
		};
		
		vehicles = new SortedArrayList<>(vehicleComparator);
	 }

	@Override
	public void advance() {
		int baseVelocity = calculateBaseVelocity();
		int obstacles = 0;
		
		for(Vehicle v : vehicles) {
			if(v.getBreakDownDuration() > 0) {
				obstacles++;
			}// if the vehicle is not waiting in a junction we set his speed
			else if(!v.getInTheJunction()){ 
				v.setCurrentSpeed(baseVelocity/calculateReductionFactor(obstacles));
			}
			v.advance();
		}
		vehicles.sort(vehicleComparator);
	}
	
	public void removeVehicle(Vehicle vehicle) {
		vehicles.remove(vehicle);
	}

	public void enterVehicle(Vehicle vehicle) {
		if(!vehicles.contains(vehicle)) {
			vehicles.add(vehicle);
			vehicles.sort(vehicleComparator);
		}
	}
	
	protected int calculateBaseVelocity() {
		int result = Math.min(maxSpeed,
				             (maxSpeed / Math.max(vehicles.size(), 1)) + 1);
		return result;
	}
	
	protected int calculateReductionFactor(int obstacles) {
		return ((obstacles > 0) ? 2 : 1);
	}
	
	public void vehicleArrivesToJunction(Vehicle v) {
		destinationJunction.enterVehicleToJunction(id,v);	
	}
	
	
	@Override
	protected void completeSectionDetails(IniSection is) {
		String result = "";
		
		if(vehicles.size() > 0) {
			for(int i = 0; i < vehicles.size(); i++) {
				result += "(" + vehicles.get(i).id + ","
							  + vehicles.get(i).getLocation() + ")";
				
				if(!(i == vehicles.size() - 1))
						result += ",";
			}
			is.setValue("state", result);
		}
		else
			is.setValue("state",result);
	}
	
	public int getRoadLength() {
		return length;
	}
	
	public int getMaxSpeed() {
		return maxSpeed;
	}

	public GenericJunction<?> getOriginJunction() {
		return originJunction;
	}

	public GenericJunction<?> getDestinationJunction() {
		return destinationJunction;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	
	@Override
	protected String getSectionName() {
		return "road_report";
	}

	
}

