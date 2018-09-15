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
		creaOPcionesPLantillas(mainWindow);
		creaOpcionesEventos(mainWindow);
	}

	// OPCIONES EVENTOS
	// ----------------------------------------------------------------
	public void creaOpcionesEventos(MainView mainWindow) {

		// CARGAR EVENTOS
		JMenuItem cargar = new JMenuItem("Cargar");

		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.loadEventsFile();

			}
		});

		this.add(cargar);
		this.addSeparator();

		// SALVAR EVENTOS
		JMenuItem salvar = new JMenuItem("Salvar");

		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.salvaEventos();

			}
		});

		this.add(salvar);
		this.addSeparator();

		// LIMPIAR EVENTOS
		JMenuItem limpiar = new JMenuItem("Limpiar");
		limpiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.clearAreaEventos();

			}
		});

		this.add(limpiar);

	}

	// PLANTILLAS
	// ---------------------------------------------------------------------------
	public void creaOPcionesPLantillas(MainView mainWindow) {
		
		JMenu plantillas = new JMenu("Nueva plantilla");
		this.add(plantillas);
		plantillas.setPreferredSize(new Dimension(150, 20));
		
		// Recorre los constructores de eventos imprimiendo 
		// su nombre para ponerlos como opcion en el menu Nueva Plantilla
		// Parser Eventos devuelve el array de constructores
		
		for (EventsConstructor ce : EventsParser.getEventsConstructorArray()) {
			
			 JMenuItem mi = new JMenuItem(ce.toString());
			 
			 mi.addActionListener(new ActionListener() {
				 // Inserta cada plantilla en el editor de eventos + salto de linea
				 @Override
				 public void actionPerformed(ActionEvent e) {
					 mainWindow.insertaEnEditorEventos(ce.template()
							 				+ System.lineSeparator());
				 }
				 
			});
			 
			plantillas.add(mi);	
		}
		this.addSeparator();
	}
	

}
