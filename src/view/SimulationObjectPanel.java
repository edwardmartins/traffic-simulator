package view;


import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
// Panel de objetos genericos
public class SimulationObjectPanel<T> extends JPanel {

	private ListModel<T> listModel; // Modelo de la lista con el tipo generico
	private JList<T> objList; // El Jlist al que se le pasa el modelo de la lista

	public SimulationObjectPanel(String titulo) {

		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder(
				MainView.defaultBorder,
				titulo,
				TitledBorder.LEFT,
				TitledBorder.TOP));	
		
		// A Jlist se le pasa como parametro el modelo de la lista
		this.listModel = new ListModel<T>();
		this.objList = new JList<T>(this.listModel);

		addCleanSelectionListner(objList);

		this.add(new JScrollPane(objList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

	}

	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {
			
			// limpiar la seleccion de items pulsando "c"
			public void keyTyped(KeyEvent e) {
				
				if (e.getKeyChar() == ReportsDialog.TECLALIMPIAR)
					list.clearSelection();
			}

			@Override
			public void keyPressed(KeyEvent arg0) { }

			@Override
			public void keyReleased(KeyEvent arg0) { }
		});
	}

	// Recorre la lista y te devuelve los elementos seleccionados de la lista
	// Para cada lista te devuelve, los vehiculos, cruzes o carreteras seleccionados
	// esta implementado en dialogoInformes
	//---------------------------------------------------------------------------
	public List<T> getSelectedItems() { 
		
		// Lista que devuelve
		List<T> lista = new ArrayList<>();
		
		// Los elementos cuyos indices han sido seleccionados los añade a la lista
		// Get selected indices devuelve los indices seleccionados
		for (int i : this.objList.getSelectedIndices()) {
			
			lista.add(listModel.getElementAt(i));
		}
		return lista;
	}

	// Coloca la lista por argumento en la lista del modelo
	public void setList(List<T> lista) {
		this.listModel.setList(lista);
	}

}
