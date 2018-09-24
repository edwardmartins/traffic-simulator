package view;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controller;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.Road;

@SuppressWarnings("serial")
public class RoadTableModel extends TableModel<Road>{

	public RoadTableModel(String[] columnIdEvents, Controller ctrl) {
		super(columnIdEvents, ctrl);
		
	}
	
	// Necesariy to visualize data
	// -----------------------------------------------------------------------
	public Object getValueAt(int rowIndex, int colIndex) {
		
		Object s = null;
		switch (colIndex) {
		case 0:
			s = this.list.get(rowIndex).getId();
			break;
		case 1:
			s = this.list.get(rowIndex).getOriginJunction();
			break;
		case 2:
			s = this.list.get(rowIndex).getDestinationJunction();
			break;
		case 3:
			s = this.list.get(rowIndex).getRoadLength();
			break;
		case 4:
			s = this.list.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = this.list.get(rowIndex).getVehicles();
			break;
			// default
		default: assert (false);
		}
		return s;
	}

	// OBSERVERS
	// ----------------------------------------------------------------------------
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events,
			SimulationError e) {}

	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				list = map.getRoads(); 
				fireTableStructureChanged();  
			}
		});
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.list = map.getRoads();
		this.fireTableStructureChanged();
	}

}
