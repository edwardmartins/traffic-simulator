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
	

	public ReportsPanel(String titulo, boolean editable, Controller ctrl) {
		
		super(titulo, editable);
		ctrl.addObserver(this);
	}

	// Observadores
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int tiempo, RoadMap map,
			List<Event> events, SimulationError e) { }

	@Override
	public void advance(int tiempo, RoadMap mapa,
			List<Event> events) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				textArea.append(mapa.generateReport(tiempo));
			}
		});
		
	}

	@Override
	public void addEvent(int tiempo, RoadMap mapa,
			List<Event> events) { }

	@Override
	public void reset(int tiempo, RoadMap mapa,
			List<Event> events) {
	
		this.limpiarAreaDeTexto();
	}

}
