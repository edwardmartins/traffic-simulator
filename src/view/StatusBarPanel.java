package view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import control.Controller;
import control.Observer;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;

@SuppressWarnings("serial")
public class StatusBarPanel extends JPanel
							  implements Observer{
	
	private JLabel executionInfo;
	
	public StatusBarPanel(String message, Controller controller) {
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		executionInfo = new JLabel(message);
		this.add(executionInfo);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controller.addObserver(this);
	}
	
	
	public void setMessage(String message) {
		executionInfo.setText(message);
	}

	// OBSERVERS
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events,
			SimulationError e) { }

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				executionInfo.setText("Step: " + time + " of the Simulator");
				
			}
		});
		
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		executionInfo.setText("Event Added to the simulator");
		
	}

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		executionInfo.setText("Simulator has been reset");
		
	}

}
