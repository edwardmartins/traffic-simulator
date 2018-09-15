package view;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import control.Controller;
import control.Observer;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;


// El modelo es el que maneja los datos de la tabla
// Todas las operaciones sobre una tabla han de hacerse sobre su modelo
@SuppressWarnings("serial")
public abstract class TableModel<T> extends DefaultTableModel
									 implements Observer {

	protected String[] columnIds; 
	protected List<T> list; // generica, implementa una lista de los elementos tipo T

	public TableModel(String[] columnIdEventos, Controller ctrl) {
		
		// Lista del tipo especificado en T
		this.list = null;
		
		// Los ids de las columnas
		this.columnIds = columnIdEventos; 
		
		// Añade el observador que luego se registrara en el modelo ( simulador )
		ctrl.addObserver(this);

	}

	@Override // para que se muestren los nombres de las columnas
	public String getColumnName(int col) {
		return this.columnIds[col];
	}
	
	// Obligatorio implementar estod dos metodos para que se vea la tabla
	// ----------------------------------------------------------------------------
	@Override
	public int getColumnCount() {
		return this.columnIds.length;
	}

	@Override
	public int getRowCount() {
		return this.list == null ? 0 : this.list.size();
	}
	

	// Sobreescribir este metodo para que no sean editables las celdas
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	// Observadores, se implementan en las subclases de las tablas 
	// que son las que cambian cuando se producen cambios en el modelo
	//-----------------------------------------------------------------
	@Override
	public abstract void simulatorError(int tiempo, RoadMap map, List<Event> events,
			SimulationError e);

	@Override
	public abstract void advance(int tiempo, RoadMap mapa, List<Event> events);

	@Override
	public abstract void addEvent(int tiempo, RoadMap mapa, List<Event> events);

	@Override
	public abstract void reset(int tiempo, RoadMap mapa, List<Event> events);
}
