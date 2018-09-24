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

	public VehiclesTableModel(String[] columnIdEvents, Controller ctrl) {
		super(columnIdEvents, ctrl);
			
		
	}

	// Necesariy to visualize data
	// -------------------------------------------------------
	public Object getValueAt(int rowIndex, int colIndex) {
		Object s = null;
		switch (colIndex) {
		case 0:
			s = this.list.get(rowIndex).getId();
			break;
		case 1:
			s = this.list.get(rowIndex).getRoad();
			break;
		case 2:
			s = this.list.get(rowIndex).getLocation();
			break;
		case 3:
			s = this.list.get(rowIndex).getCurrentSpeed();
			break;
		case 4:
			s = this.list.get(rowIndex).getKilometrage();
			break;
		case 5:
			s = this.list.get(rowIndex).getBreakDownDuration();
			break;
		case 6:
			s = this.list.get(rowIndex).getItinerary();
			break;
			// default
		default: assert (false);
		}
		return s;
	}

	// Observers
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, 
			SimulationError e) {}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				list = map.getVehicles(); 
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.list = map.getVehicles(); 
		this.fireTableStructureChanged();
	}

}
