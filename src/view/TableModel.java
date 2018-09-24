package view;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import control.Controller;
import control.Observer;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;


@SuppressWarnings("serial")
public abstract class TableModel<T> extends DefaultTableModel
									 implements Observer {

	protected String[] columnIds; 
	protected List<T> list; 

	public TableModel(String[] columnIdEvents, Controller ctrl) {
		this.list = null;
		this.columnIds = columnIdEvents; 
		ctrl.addObserver(this);

	}

	@Override 
	public String getColumnName(int col) {
		return this.columnIds[col];
	}
	
	// MUST IMPLEMENT THIS METHODS TO SEE THE TABLE
	// ----------------------------------------------------------------------------
	@Override
	public int getColumnCount() {
		return this.columnIds.length;
	}

	@Override
	public int getRowCount() {
		return this.list == null ? 0 : this.list.size();
	}
	// ----------------------------------------------------------------------------

	// it can't be possible to modify a cell
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	
	// OBSERVERS IMPLEMENTED IN THE SUBCLASSES
	//-----------------------------------------------------------------
	@Override
	public abstract void simulatorError(int time, RoadMap map, List<Event> events,
			SimulationError e);

	@Override
	public abstract void advance(int time, RoadMap map, List<Event> events);

	@Override
	public abstract void addEvent(int time, RoadMap map, List<Event> events);

	@Override
	public abstract void reset(int time, RoadMap map, List<Event> events);
}
