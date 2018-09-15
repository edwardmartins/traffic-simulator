package control;

import java.util.List;

import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;

public interface Observer {

	// Notyfy errors
	public void simulatorError(int time, RoadMap map,
			List<Event> events, SimulationError e);

	// Notify the advancement of the simulation
	public void advance(int time, RoadMap map,
			List<Event> events);

	// Notify we add a new event
	public void addEvent(int time, RoadMap map,
			List<Event> events);

	// Notifiy we restart the simulator
	public void reset(int time, RoadMap map,
			List<Event> events);

}
