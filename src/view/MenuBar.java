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
	
	JMenu fileMenu;
	JMenu simulatorMenu;
	JMenu reportsMenu;
	
	// CREATES MENU
	//---------------------------------------------------------------------------
	public MenuBar(MainView mainWindow, Controller controller) {
		
		super(); 

		// FILE MENU
		fileMenu = new JMenu("Files");
		this.add(fileMenu);
		createOptionsFromFileMenu(fileMenu,mainWindow);

		// SIMULATOR MENU
		simulatorMenu = new JMenu("Simulator");
		this.add(simulatorMenu);
		createOptionsFromSimulatorMenu(simulatorMenu, controller, mainWindow);

		// REPORTS MENU
		reportsMenu = new JMenu("Reports");
		this.add(reportsMenu);
		createOptionsFromReportsMenu(reportsMenu, mainWindow);
		
	}

	// CREATE OPTIONS FROM FILE MENU
	// ------------------------------------------------------------------------------
	private void createOptionsFromFileMenu(JMenu fileMenu, MainView mainWindow) {

		// LOAD EVENTS
		JMenuItem loadEvents = new JMenuItem("Load Events");
		loadEvents.setMnemonic(KeyEvent.VK_L);
		loadEvents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
							  ActionEvent.ALT_MASK));
		// action
		loadEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.loadEventsFile();
				
			}
		});

		// SAVE EVENTS
		JMenuItem saveEvents = new JMenuItem("Save Events");
		saveEvents.setMnemonic(KeyEvent.VK_S);
		saveEvents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
						      ActionEvent.ALT_MASK));
		
		// action
		saveEvents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				mainWindow.saveEvents();
				
			}
		});

		// SAVE REPORTS
		JMenuItem saveReports = new JMenuItem("Save Reports");
		saveReports.setMnemonic(KeyEvent.VK_R);
		saveReports.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
									  ActionEvent.ALT_MASK));
		
		// action
		saveReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.saveReports();
				
			}
		});

		// EXIT
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		
		// action
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.exit();
				
			}
		});

		
		// Add options to menu
		fileMenu.add(loadEvents);
		fileMenu.addSeparator();
		fileMenu.add(saveEvents);
		fileMenu.addSeparator();
		fileMenu.add(saveReports);
		fileMenu.addSeparator();
		fileMenu.add(exit);

	}
	
	
	// CREATE OPTIONS FROM SIMULATOR MENU
	// ------------------------------------------------------------------------------
	private void createOptionsFromSimulatorMenu(JMenu simulatorMenu, Controller controller,
										  MainView mainWindow) {
		// EXECUTE
		JMenuItem execute = new JMenuItem("Execute");
		
		// action
		execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.executeSimulator();
			}
		});
		
		// RESET
		JMenuItem reset = new JMenuItem("Reset");
		
		// action
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.reset();
				
			}
		});
		
		// Add options to menu
		simulatorMenu.add(execute);
		simulatorMenu.addSeparator();
		simulatorMenu.add(reset);
		
	}
	
	

   // CREATE OPTIONS FROM REPORTS MENU
   // -------------------------------------------------------------------------------------
	private void createOptionsFromReportsMenu(JMenu reportsMenu,MainView mainWindow) {
		
		// GENERATE REPORTS
		JMenuItem generateReports = new JMenuItem("Generate Reports");
		
		// action
		generateReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.showReportsDialog();
				
			}
		});
		
		// CLEAR REPORTS
		JMenuItem clearReports = new JMenuItem("Clear Reports");
		
		// action
		clearReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.clearReportsArea();
				
			}
		});
		
		// Add options to menu
		reportsMenu.add(generateReports);
		reportsMenu.addSeparator();
		reportsMenu.add(clearReports);
		
	}
	
	// DISABLED MENUS
	// -------------------------------------------------------------------------------------
	public void enabledMenus() {
		fileMenu.setEnabled(true);
		simulatorMenu.setEnabled(true);
		reportsMenu.setEnabled(true);
	}
	
	// DISABLED MENUS
	// -------------------------------------------------------------------------------------
	public void disabledMenus() {
		fileMenu.setEnabled(false);
		simulatorMenu.setEnabled(false);
		reportsMenu.setEnabled(false);
	}
	
	
}
