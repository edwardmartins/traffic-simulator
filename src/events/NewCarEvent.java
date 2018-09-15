package events;

import java.util.List;

import exceptions.SimulationError;
import model.objects.Car;
import model.objects.GenericJunction;
import model.objects.Vehicle;

public class NewCarEvent extends NewVehicleEvent{
	
	protected Integer resistance;
	protected Integer maxBreakDownDuration;
	protected double breakDownProbability;
	protected long seed;

	public NewCarEvent(int time, String id, int vMax,
			String[] itinerary,Integer resist, double faultProbability,
			int faultDuration, long semilla) {
		
		super(time, id, vMax, itinerary);
		resistance = resist;
		breakDownProbability = faultProbability;
		maxBreakDownDuration = faultDuration;
		seed = semilla;
	}

	@Override
	protected Vehicle createsVehicle(List<GenericJunction<?>> iti) throws SimulationError {
		return new Car(this.id, this.maxSpeed, 
				resistance, breakDownProbability, seed, maxBreakDownDuration, iti);
	}
	
}
