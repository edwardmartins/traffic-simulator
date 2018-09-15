package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")

// Sirve para las 4 tablas de la ventana principal(Eventos, Vehiculos, Carreteras, Cruzes)
// Las cuales tienen un modelo de tabla distinto por eso esta clase es generica
public class TablePanel<T> extends JPanel {

	private TableModel<T> modelo;
	private JTable tabla;

	// El modelo se le pasa con newModeloTabla en Ventana principal
	// ya que hemos creado tb clases para los diferentes modelos
	public TablePanel(String bordeId, TableModel<T> modelo){
		
		// Layout
		this.setLayout(new GridLayout(1,1));
		
		// Borde
		this.setBorder(BorderFactory.createTitledBorder(
				MainView.defaultBorder,
				bordeId,
				TitledBorder.LEFT,
				TitledBorder.TOP));	
		
		// Tipo de modelo de la tabla, esta en la clase ModeloTabla
		this.modelo = modelo;
		this.tabla = new JTable(this.modelo);
		
		this.add(new JScrollPane(tabla,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	

}
