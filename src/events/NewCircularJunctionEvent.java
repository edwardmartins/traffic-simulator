package events;

import model.objects.CircularJunction;
import model.objects.GenericJunction;

public class NewCircularJunctionEvent extends NewJunctionEvent {

	protected Integer maxValueInterval;
	protected Integer minValueInterval;
	
	public NewCircularJunctionEvent(int time, String id, int min, int max) {
		super(time, id);
		minValueInterval = min;
		maxValueInterval = max;
	}

	@Override
	protected GenericJunction<?> createsJunction() {
		return new CircularJunction(id, minValueInterval, maxValueInterval);
	}

}
