package view;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class EventsEditorPanel extends TextAreaPanel {

	public EventsEditorPanel(String title, boolean editable,
			String text, MainView mainWindow) {
		
		super(title, editable);
		this.setTexto(text);
		
		MyPopUpMenu myPopUpMenu = new MyPopUpMenu(mainWindow);
		
		this.textArea.add(myPopUpMenu);
		this.textArea.setComponentPopupMenu(myPopUpMenu); 
		
		
		this.textArea.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				if (e.isPopupTrigger() && textArea.isEnabled())
					 myPopUpMenu.show(e.getComponent(), e.getX(), e.getY());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}

}
