package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import events.constructors.EventsConstructor;
import utils.EventsParser;

@SuppressWarnings("serial")
public class MyPopUpMenu extends JPopupMenu {

	public MyPopUpMenu(MainView mainWindow) {

		super(); // JPopupMenu = new JPopUp menu()
		createTemplateOptions(mainWindow);
		createEventsOptions(mainWindow);
	}

	// EVENTS OPTIONS
	// ----------------------------------------------------------------
	public void createEventsOptions(MainView mainWindow) {

		// LOAD EVENTS
		JMenuItem load = new JMenuItem("Load");

		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.loadEventsFile();

			}
		});

		this.add(load);
		this.addSeparator();

		// SAVE EVENTS
		JMenuItem salvar = new JMenuItem("Save");

		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.saveEvents();

			}
		});

		this.add(salvar);
		this.addSeparator();

		// CLEAR EVENTS
		JMenuItem limpiar = new JMenuItem("Clear");
		limpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.clearEventsArea();

			}
		});

		this.add(limpiar);

	}

	// TEMPLATES
	// ---------------------------------------------------------------------------
	public void createTemplateOptions(MainView mainWindow) {
		
		JMenu templates = new JMenu("New Template");
		this.add(templates);
		templates.setPreferredSize(new Dimension(150, 20));
		
		for (EventsConstructor ce : EventsParser.getEventsConstructorArray()) {
			
			 JMenuItem mi = new JMenuItem(ce.toString());
			 
			 mi.addActionListener(new ActionListener() {
			
				 @Override
				 public void actionPerformed(ActionEvent e) {
					 mainWindow.insertInEventsEditor(ce.template()
							 				+ System.lineSeparator());
				 }
				 
			});
			 
			templates.add(mi);	
		}
		this.addSeparator();
	}
	

}
