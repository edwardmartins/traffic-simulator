package view;


import java.util.List;

import javax.swing.SwingUtilities;

import control.Controller;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;

@SuppressWarnings("serial")
public class EventsTableModel extends TableModel<Event> {

	public EventsTableModel(String[] columnIdEvents, Controller ctrl) {
		super(columnIdEvents, ctrl);
	}

	// Necessary to visualizate data
	// -----------------------------------------------------------------------
	public Object getValueAt(int rowIndex, int colIndex) {
		Object s = null;
		switch (colIndex) {
			case 0:
				s = rowIndex;
				break;
			case 1:
				s = this.list.get(rowIndex).getTime();
				break;
			case 2:
				s = this.list.get(rowIndex).toString();
				break;
			default: assert (false);
		}
		return s;
	}

	// Observers
	// -----------------------------------------------------------------------
	@Override
	public void advance(int time, RoadMap map, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				list = events; 
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				list = events; 
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.list = events; 
		this.fireTableStructureChanged();
	}

	@Override
	public void simulatorError(int time, RoadMap map, 
			List<Event> events, SimulationError e) {}

}
