package events;

import exceptions.SimulationError;
import model.map.RoadMap;

public abstract class Event {
	
	protected Integer time; // Each event executes in a concrete time
	
	public Event(int tiempo) { 
		time = tiempo;
	}
	
	public int getTime() {
		return time; 
	}
	
	// Adds each event to the map
	public abstract void execute(RoadMap mapa) throws SimulationError;

}
