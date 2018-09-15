package model.simulator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import control.Observer;
import control.Subject;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;
import utils.SortedArrayList;


public class TrafficSimulator implements Subject<Observer> {

	private List<Observer> observers; // list of observers (views)
	private RoadMap map;
	private List<Event> events;
	private int time;

	
	public TrafficSimulator() {
		observers = new ArrayList<>();
		reset();
	}

	// RESET
	// ---------------------------------------------------------------------------
	public void reset() {

		map = new RoadMap();
		time = 0;

		// Events ordered by time
		Comparator<Event> cmp = new Comparator<Event>() {
			@Override
			public int compare(Event o1, Event o2) {
				if (o1.getTime() == o2.getTime())
					return 0;
				else if (o1.getTime() < o2.getTime())
					return -1;
				else
					return 1;
			}
		};
	
		events = new SortedArrayList<Event>(cmp);

		notifyReset();
	}

	// EXECUTE
	// ---------------------------------------------------------------------------
	public void execute(int simulationSteps, OutputStream outputFile) 
			throws SimulationError {

		int timelimit = time + simulationSteps - 1;

		while (time <= timelimit) {

			try {

				for (Iterator<Event> iterator = events.iterator(); iterator.hasNext();) {
					Event event = iterator.next();
					
					// Execute the events according to the corresponding time
					if (event.getTime() == time) {
						
						event.execute(map);
						
						iterator.remove(); 
					}
				}

				map.update();
				time++;
				notifySimulationAdvancement();

				if (outputFile != null)
					outputFile.write(map.generateReport(time).getBytes());
				
			// graphic mode	
			} catch (SimulationError e) {
				notifyError(e);
				throw new SimulationError();

			// just console mode
			} catch (IOException e) {
				throw new SimulationError("Error writting in the outputFile");
			}
		}
	}

	// INSERT EVENTS
	// ---------------------------------------------------------------------------
	public void insertEvent(Event e) throws SimulationError {

		if (e != null) {

			if (e.getTime() < time) {

				SimulationError err = new SimulationError();

				notifyError(err);
				throw err;

			} else {
				
				events.add(e);
				notifyNewEvent();
			}
		} else {
			SimulationError err = new SimulationError();
			notifyError(err); 
			throw err;
		}
	}

	// NOTIFICATIONS
	// ------------------------------------------------------------------------
	
	private void notifyNewEvent() {
	
		for (Observer obs : observers) {
			obs.addEvent(time, map, events);
		}

	}

	
	private void notifySimulationAdvancement() {
	
		for (Observer obs : observers) {
				obs.advance(time, map, events);
			}
	
	}

	private void notifyReset() {
		
		for (Observer obs : observers) {
			obs.reset(time, map, events);
		}
	}

	
	private void notifyError(SimulationError err) {

		for (Observer obs : observers) {
			obs.simulatorError(time, map, events, err);
		}
	}
	
	// ADD AND REMOVE
	// ------------------------------------------------------------------------

	@Override
	public void addObserver(Observer obs) {

		if (obs != null && !observers.contains(obs)) {
			observers.add(obs);
		}

	}

	@Override
	public void removeObserver(Observer obs) {

		if (obs != null && observers.contains(obs)) {
			observers.remove(obs);
		}

	}
}
