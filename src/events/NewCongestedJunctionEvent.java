package events;

import model.objects.CongestedJunction;
import model.objects.GenericJunction;

public class NewCongestedJunctionEvent extends NewJunctionEvent {

	public NewCongestedJunctionEvent(int time, String id) {
		super(time, id);
	}

	@Override
	protected GenericJunction<?> createsJunction() {
		return new CongestedJunction(this.id);
	}
}
