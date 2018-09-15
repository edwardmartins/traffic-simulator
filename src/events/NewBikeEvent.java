package events;

import java.util.List;

import exceptions.SimulationError;
import model.objects.Bike;
import model.objects.GenericJunction;
import model.objects.Vehicle;

public class NewBikeEvent extends NewVehicleEvent {

	public NewBikeEvent(int time, String id, int maxSpeed,
								String[] itinerary) {
		
		super(time, id, maxSpeed, itinerary);
	}

	@Override
	protected Vehicle createsVehicle(List<GenericJunction<?>> iti) throws SimulationError {
		return new Bike(this.id, this.maxSpeed, iti);
	}
	
}
