package events;

import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.SimpleJunction;
import model.objects.GenericJunction;

public class NewJunctionEvent extends Event {

	protected String id;

	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}

	@Override  
	public void execute(RoadMap map) throws SimulationError {
		GenericJunction<?> junction = createsJunction();
		map.addJunction(id, junction);
	}
	
	protected GenericJunction<?> createsJunction() {
		 return new SimpleJunction(id);	 
	}
	
	public String  toString() {
		return "New Junction  " + id;
	}

}
