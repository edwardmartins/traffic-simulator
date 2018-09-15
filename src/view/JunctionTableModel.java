package view;

import java.util.List;

import javax.swing.SwingUtilities;

import control.Controller;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.GenericJunction;


@SuppressWarnings("serial")
public class JunctionTableModel extends TableModel<GenericJunction<?>> {

	public JunctionTableModel(String[] columnIdEvents, Controller ctrl) {
		super(columnIdEvents, ctrl);

	}

	// Necessary to visualizate data
	// -----------------------------------------------------------------------
	public Object getValueAt(int rowIndex, int colIndex) {
		Object s = null;
		switch (colIndex) {
		case 0:
			s = this.list.get(rowIndex).getId();
			break;
		case 1:
			s = this.list.get(rowIndex).entryRoadsWithGreenLights(); 
			break;
		case 2:
			s = this.list.get(rowIndex).entryRoadsWithRedLights();
			break;
		default:
			assert (false);
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
				list = map.getJunctions(); 
				fireTableStructureChanged();
			}
		});
		

	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) { }

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.list = map.getJunctions();
		this.fireTableStructureChanged();

	}

}
