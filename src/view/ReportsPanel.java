package view;

import java.util.List;
import javax.swing.SwingUtilities;
import control.Controller;
import control.Observer;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;

@SuppressWarnings("serial")
public class ReportsPanel extends TextAreaPanel 
						   implements Observer {
	

	public ReportsPanel(String title, boolean editable, Controller ctrl) {
		
		super(title, editable);
		ctrl.addObserver(this);
	}

	// OBSERVERS
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int time, RoadMap map,
			List<Event> events, SimulationError e) { }

	@Override
	public void advance(int time, RoadMap map,
			List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				textArea.append(map.generateReport(time));
			}
		});
		
	}

	@Override
	public void addEvent(int time, RoadMap map,
			List<Event> events) { }

	@Override
	public void reset(int time, RoadMap mapa,
			List<Event> events) {
	
		this.clearTextArea();
	}

}
