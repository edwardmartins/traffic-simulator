package view;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class TablePanel<T> extends JPanel {

	private TableModel<T> model;
	private JTable table;


	public TablePanel(String bordeId, TableModel<T> modelo){
		
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder(
				MainView.defaultBorder,
				bordeId,
				TitledBorder.LEFT,
				TitledBorder.TOP));	
		
		this.model = modelo;
		this.table = new JTable(this.model);
		
		this.add(new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	

}
