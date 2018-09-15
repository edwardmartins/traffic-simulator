package view;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controller;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.Vehicle;

@SuppressWarnings("serial")
public class VehiclesTableModel extends TableModel<Vehicle> {

	public VehiclesTableModel(String[] columnIdEventos, Controller ctrl) {
		super(columnIdEventos, ctrl);
			
		
	}

	// Necesario para que se visualicen los datos
	// -------------------------------------------------------
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
		case 0:
			s = this.list.get(indiceFil).getId();
			break;
		case 1:
			s = this.list.get(indiceFil).getRoad();
			break;
		case 2:
			s = this.list.get(indiceFil).getLocation();
			break;
		case 3:
			s = this.list.get(indiceFil).getCurrentSpeed();
			break;
		case 4:
			s = this.list.get(indiceFil).getKilometrage();
			break;
		case 5:
			s = this.list.get(indiceFil).getBreakDownDuration();
			break;
		case 6:
			s = this.list.get(indiceFil).getItinerary();
			break;
			// default
		default: assert (false);
		}
		return s;
	}

	// Observadores
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int tiempo, RoadMap map, List<Event> events, 
			SimulationError e) {}

	@Override
	public void advance(int tiempo, RoadMap mapa, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				list = mapa.getVehicles(); // devuelve la lista de vehiculos
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void addEvent(int tiempo, RoadMap mapa, List<Event> events) {}

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		this.list = mapa.getVehicles(); 
		this.fireTableStructureChanged();
	}

}
