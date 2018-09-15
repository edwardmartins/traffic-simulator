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
	
	private JLabel infoEjecucion;
	
	public StatusBarPanel(String mensaje, Controller controller) {
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		infoEjecucion = new JLabel(mensaje);
		
		// Añado la etiqueta al panel
		// la etiqueta es la que va a ir mostrando el mensaje
		this.add(infoEjecucion);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controller.addObserver(this);
	}
	
	//Para que la ventana principal se comunique con la barra de estado
	public void setMensaje(String mensaje) {
		infoEjecucion.setText(mensaje);
	}

	// Observadores
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int tiempo, RoadMap map, List<Event> events,
			SimulationError e) { }

	@Override
	public void advance(int tiempo, RoadMap mapa, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				infoEjecucion.setText("Step: " + tiempo + " of the Simulator");
				
			}
		});
		
		
	}

	@Override
	public void addEvent(int tiempo, RoadMap mapa, List<Event> events) {
		infoEjecucion.setText("Event Add to simulator");
		
	}

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		infoEjecucion.setText("Simulator has been reset");
		
	}

}
