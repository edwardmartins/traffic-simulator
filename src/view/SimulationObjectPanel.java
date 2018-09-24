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
public class SimulationObjectPanel<T> extends JPanel {

	private ListModel<T> listModel; 
	private JList<T> objList; 

	public SimulationObjectPanel(String title) {

		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder(
				MainView.defaultBorder,
				title,
				TitledBorder.LEFT,
				TitledBorder.TOP));	
		
	
		this.listModel = new ListModel<T>();
		this.objList = new JList<T>(this.listModel);

		addCleanSelectionListner(objList);

		this.add(new JScrollPane(objList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

	}

	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				
				if (e.getKeyChar() == ReportsDialog.CLEAR_KEY) // CLEAR_KEY = 'c'
					list.clearSelection();
			}

			@Override
			public void keyPressed(KeyEvent arg0) { }

			@Override
			public void keyReleased(KeyEvent arg0) { }
		});
	}

	// Used in reports dialog to get the selected items
	public List<T> getSelectedItems() { 
		
		List<T> selectedIndexList = new ArrayList<>();
		
		for (int i : this.objList.getSelectedIndices()) {
			
			selectedIndexList.add(listModel.getElementAt(i));
		}
		return selectedIndexList;
	}

	
	public void setList(List<T> list) {
		this.listModel.setList(list);
	}

}
