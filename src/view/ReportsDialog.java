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

	protected static final char TECLALIMPIAR = 'c';
	private ButtonsReportsDialog buttonsReportsDialog;
	private SimulationObjectPanel<Vehicle> panelVehiculos;
	private SimulationObjectPanel<Road> panelCarreteras;
	private SimulationObjectPanel<GenericJunction<?>> panelCruces;

	// Un Jdialog es como un frame al que se le pueden añadir paneles
	public ReportsDialog(MainView mainWindow, Controller controller) {
		
		this.setTitle("Generar Informes");
		controller.addObserver(this);
		initGUI(mainWindow);
	}
	
	// GUI
	//----------------------------------------------------------------------------------
	private void initGUI(MainView mainWindow) {
		
		// CREO PANEL PRINCIPAL
		JPanel panelPrincipal = new JPanel(new BorderLayout());
		this.add(panelPrincipal);
		
		// CREO PPANEL SUPERIOR
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		
		// Texto inicial
		JLabel textoInicial1 = new JLabel();
		JLabel textoInicial2 = new JLabel();
		JLabel textoInicial3 = new JLabel();
		
		textoInicial1.setText("Selecciona objetos para generar Informes." + "\n" );
		textoInicial2.setText("Usa 'c' para deseleccionar todos." + "\n" );
		textoInicial3.setText("Usa Ctrl + A para seleccionar todos. ");
						
		// Añado el texto al panel superior
		panelSuperior.add(textoInicial1);
		panelSuperior.add(textoInicial2);
		panelSuperior.add(textoInicial3);
		
		// Genera Espacio ( Separacion entre componentes )
		panelSuperior.add(Box.createRigidArea(new Dimension(0,20)));

		// Añado el panel superior al principal
		panelPrincipal.add(panelSuperior,BorderLayout.PAGE_START);
		
		// CREO PANEL CENTRAL
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.X_AXIS));
		
		// Creo objetos del panel central ( 3 paneles con 1 lista por panel )
		// argumento es el titulo de la lista
		this.panelVehiculos = new SimulationObjectPanel<Vehicle>("Vehiculos"); 
		this.panelCarreteras = new SimulationObjectPanel<Road>("Carreteras");
		this.panelCruces = new SimulationObjectPanel<GenericJunction<?>>("Cruces");
		

		// Los añado al panel central
		panelCentral.add(panelVehiculos);
		panelCentral.add(panelCarreteras);
		panelCentral.add(panelCruces);

		// Añado el panel central al principal
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);

		// Creo el panel de botones y lo añado al panel principal
		this.buttonsReportsDialog = new ButtonsReportsDialog(this, mainWindow);
		panelPrincipal.add(buttonsReportsDialog, BorderLayout.PAGE_END);
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.pack();
		
	}
	
	// METODOS
	//-----------------------------------------------------------------------------
	
	// Voy jugando con el setVisible del dialog para ocultarlo o mostrarlo
	
	// Hace visible el Jdialog
	public void mostrar() {
		this.setVisible(true);
	}
	
	// Oculta el Jdialog
	public void ocultar() {
		this.setVisible(false);
	}

	// Coloca cada lista del mapa en la lista del Jdialog
	private void setMapa(RoadMap mapa) {
		this.panelVehiculos.setList(mapa.getVehicles());
		this.panelCarreteras.setList(mapa.getRoads());
		this.panelCruces.setList(mapa.getJunctions());
	}

	// Devuelve los elementos seleccionados de cada lista
	public List<Vehicle> getSelectedVehicles() {
		return this.panelVehiculos.getSelectedItems();
	}

	public List<Road> getSelectedRoads() {
		return this.panelCarreteras.getSelectedItems();
	}

	public List<GenericJunction<?>> getSelectedJunctions() {
		return this.panelCruces.getSelectedItems();
	}
	
	// OBSERVADORES
	// -----------------------------------------------------------------------------

	@Override
	public void simulatorError(int tiempo, RoadMap map,
			List<Event> events, SimulationError e) {}

	@Override
	public void advance(int tiempo, RoadMap mapa, List<Event> events) {
		this.setMapa(mapa);

	}

	@Override
	public void addEvent(int tiempo, RoadMap mapa, List<Event> events) {
		this.setMapa(mapa);

	}

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		this.setMapa(mapa);

	}

}
