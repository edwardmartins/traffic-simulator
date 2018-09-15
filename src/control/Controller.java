package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import events.Event;
import exceptions.SimulationError;
import ini.Ini;
import ini.IniSection;
import model.simulator.TrafficSimulator;
import utils.EventsParser;


public class Controller {

	private TrafficSimulator simulator;
	private OutputStream outputFile;
	private InputStream inputfile;
	private int steps;

	public Controller(TrafficSimulator sim, int stps, InputStream is, OutputStream os) {
		simulator = sim;
		inputfile = is;
		outputFile = os;
		steps = stps;
	}
	
	// Controller adds and removes the views to/from the model
	public void addObserver(Observer obs) {
		simulator.addObserver(obs);
	}
	
	public void removeObserver(Observer obs) {
		simulator.removeObserver(obs);
	}
	
	// Executes in graphical mode
	public void execute(int steps) {
		
		try {
			// Second argument(outputFile) is null cause output is shown in the interface
			simulator.execute(steps, null);
			
		} catch (SimulationError e) {
			// Resets the simulator
			reset();
		}
	}
	
	// Reset
	public void reset() {
		simulator.reset();
	}

	
	// Executes in console mode
	public void execute(){
		 
		try {
			loadEvents(inputfile);
			simulator.execute(steps, outputFile);

		} catch (SimulationError e) {
			System.out.println(e.getMessage());
		}
	}

	public void loadEvents(InputStream inStream) throws SimulationError {

		Ini ini;
		
		try {
			// Reads the ini file and store it at "ini"
			ini = new Ini(inStream);
		} catch (IOException e) {
			throw new SimulationError("Error at reading the 'Ini' file");
		}

		// Iterates over the events and insert them into the simulator
		for (IniSection sec : ini.getSections()) {
			
			Event e = EventsParser.parseEvent(sec);
			
			if (e != null)
				this.simulator.insertEvent(e);
			else
				throw new SimulationError("Unknown event: " + sec.getTag());

		}

	}

}
