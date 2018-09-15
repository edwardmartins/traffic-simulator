package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import control.Controller;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {
	
	JMenu menuFicheros;
	JMenu menuSimulador;
	JMenu menuInformes;
	
	// CREA EL MENU
	//---------------------------------------------------------------------------
	public MenuBar(MainView mainWindow, Controller controller) {
		
		// JmenuBar representa la barra entera de menu
		// Jmenu cada desplegable de opciones
		// JmenuItem cada opcion de cada deplegable
		
		super(); // Creo la barra de menu = New JMenuBar

		// FICHEROS
		menuFicheros = new JMenu("Files");
		this.add(menuFicheros);
		creaOpcionesMenuFicheros(menuFicheros,mainWindow);

		// SIMULADOR
		menuSimulador = new JMenu("Simulator");
		this.add(menuSimulador);
		creaOpcionesMenuSimulador(menuSimulador, controller, mainWindow);

		// INFORMES
		menuInformes = new JMenu("Reports");
		this.add(menuInformes);
		creaOpcionesMenuInformes(menuInformes, mainWindow);
		
	}

	// CREA OPCIONES DEL MENU FICHEROS ( MENU ITEMS )
	// ------------------------------------------------------------------------------
	private void creaOpcionesMenuFicheros(JMenu menuFicheros, MainView mainWindow) {

		// CARGAR EVENTOS
		JMenuItem cargar = new JMenuItem("Load Events");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
							  ActionEvent.ALT_MASK));
		// accion
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.loadEventsFile();
				
			}
		});

		// SALVAR EVENTOS
		JMenuItem salvar = new JMenuItem("Save Events");
		salvar.setMnemonic(KeyEvent.VK_S);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
						      ActionEvent.ALT_MASK));
		
		// accion
		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				mainWindow.salvaEventos();
				
			}
		});

		// SALVAR INFORMES
		JMenuItem salvarInformes = new JMenuItem("Save Reports");
		salvarInformes.setMnemonic(KeyEvent.VK_R);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
									  ActionEvent.ALT_MASK));
		
		// accion
		salvarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.salvaInformes();
				
			}
		});

		// EXIT
		JMenuItem salir = new JMenuItem("Exit");
		salir.setMnemonic(KeyEvent.VK_E);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.exit();
				
			}
		});

		
		// Añado las opciones al menu ficheros
		menuFicheros.add(cargar);
		menuFicheros.addSeparator();
		menuFicheros.add(salvar);
		menuFicheros.addSeparator();
		menuFicheros.add(salvarInformes);
		menuFicheros.addSeparator();
		menuFicheros.add(salir);

	}
	
	
	// CREA OPCIONES DEL MENU SIMULADOR
	// ------------------------------------------------------------------------------
	private void creaOpcionesMenuSimulador(JMenu menuSimulador, Controller controller,
										  MainView mainWindow) {
		// EJECUTA
		JMenuItem ejecuta = new JMenuItem("Execute");
		
		// accion
		ejecuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.ejecutarSimulador();
			}
		});
		
		// REINICIA
		JMenuItem reinicia = new JMenuItem("Reset");
		
		reinicia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.reset();
				
			}
		});
		
		// añado las opciones al menu simulador
		menuSimulador.add(ejecuta);
		menuSimulador.addSeparator();
		menuSimulador.add(reinicia);
		
	}
	
	
   // CREA OPCIONES DEL MENU INFORMES
   // -------------------------------------------------------------------------------------
	private void creaOpcionesMenuInformes(JMenu menuInformes,MainView mainWindow) {
		
		// GENERAR INFORMES
		JMenuItem generaInformes = new JMenuItem("Generate");
		
		// accion
		generaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.mostrarDialofgoInformes();
				
			}
		});
		
		// LIMPIAR INFORMES
		JMenuItem limpiarInformes = new JMenuItem("Clear");
		
		// accion
		limpiarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.clearReportsArea();
				
			}
		});
		
		// añado las opciones al menu informes
		menuInformes.add(generaInformes);
		menuInformes.addSeparator();
		menuInformes.add(limpiarInformes);
		
	}
	
	// DISABLED MENUS
	// -------------------------------------------------------------------------------------
	public void enabledMenus() {
		menuFicheros.setEnabled(true);
		menuSimulador.setEnabled(true);
		menuInformes.setEnabled(true);
	}
	
	// DISABLED MENUS
	// -------------------------------------------------------------------------------------
	public void disabledMenus() {
		menuFicheros.setEnabled(false);
		menuSimulador.setEnabled(false);
		menuInformes.setEnabled(false);
	}
	
	
}
