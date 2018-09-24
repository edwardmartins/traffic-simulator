package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.Controller;
import control.Observer;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;
import model.objects.Road;
import model.objects.GenericJunction;
import model.objects.Vehicle;

@SuppressWarnings("serial")
public class ReportsDialog extends JDialog 
							implements Observer{

	protected static final char CLEAR_KEY = 'c';
	private ButtonsReportsDialog buttonsReportsDialog;
	private SimulationObjectPanel<Vehicle> vehiclesPanel;
	private SimulationObjectPanel<Road> roadPanel;
	private SimulationObjectPanel<GenericJunction<?>> junctionPanel;

	public ReportsDialog(MainView mainWindow, Controller controller) {
		
		this.setTitle("Generate Reports");
		controller.addObserver(this);
		initGUI(mainWindow);
	}
	
	// GUI
	//----------------------------------------------------------------------------------
	private void initGUI(MainView mainWindow) {
		
		// PRINCIPAL PANEL
		JPanel principalPanel = new JPanel(new BorderLayout());
		this.add(principalPanel);
		
		// SUPERIOR PANEL
		JPanel superiorPanel = new JPanel();
		superiorPanel.setLayout(new BoxLayout(superiorPanel, BoxLayout.Y_AXIS));
		
		// INICIAL TEXT
		JLabel sentence1 = new JLabel();
		JLabel sentence2 = new JLabel();
		JLabel sentence3 = new JLabel();
		
		sentence1.setText("Select the different objects to generate reports" + "\n" );
		sentence2.setText("Use 'c' to clear all." + "\n" );
		sentence3.setText("Use Ctrl + A to select all. ");
						
		// ADDS THE TEXT
		superiorPanel.add(sentence1);
		superiorPanel.add(sentence2);
		superiorPanel.add(sentence3);
		
		// GENERATE SPACE
		superiorPanel.add(Box.createRigidArea(new Dimension(0,20)));

		// ADDS SUPERIOR PANEL
		principalPanel.add(superiorPanel,BorderLayout.PAGE_START);
		
		// CENTRAL PANEL
		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.X_AXIS));
		
		// CREATE OBJECTS
		this.vehiclesPanel = new SimulationObjectPanel<Vehicle>("Vehicles"); 
		this.roadPanel = new SimulationObjectPanel<Road>("Roads");
		this.junctionPanel = new SimulationObjectPanel<GenericJunction<?>>("Junctions");
		
		centralPanel.add(vehiclesPanel);
		centralPanel.add(roadPanel);
		centralPanel.add(junctionPanel);

		principalPanel.add(centralPanel, BorderLayout.CENTER);

		// CREATE BUTTONS PANEL
		this.buttonsReportsDialog = new ButtonsReportsDialog(this, mainWindow);
		principalPanel.add(buttonsReportsDialog, BorderLayout.PAGE_END);
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.pack();
		
	}
	
	public void showDialog() {
		this.setVisible(true);
	}
	
	
	public void hideDialog() {
		this.setVisible(false);
	}

	// Set the map(vehicles,roads,junction) into the dialog
	private void setMap(RoadMap mapa) {
		this.vehiclesPanel.setList(mapa.getVehicles());
		this.roadPanel.setList(mapa.getRoads());
		this.junctionPanel.setList(mapa.getJunctions());
	}

	public List<Vehicle> getSelectedVehicles() {
		return this.vehiclesPanel.getSelectedItems();
	}

	public List<Road> getSelectedRoads() {
		return this.roadPanel.getSelectedItems();
	}

	public List<GenericJunction<?>> getSelectedJunctions() {
		return this.junctionPanel.getSelectedItems();
	}
	
	// OBSERVERS
	// -----------------------------------------------------------------------------
	@Override
	public void simulatorError(int tiempo, RoadMap map,
			List<Event> events, SimulationError e) {}

	@Override
	public void advance(int tiempo, RoadMap mapa, List<Event> events) {
		this.setMap(mapa);

	}

	@Override
	public void addEvent(int tiempo, RoadMap mapa, List<Event> events) {
		this.setMap(mapa);

	}

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		this.setMap(mapa);

	}

}
