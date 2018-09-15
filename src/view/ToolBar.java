package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import control.Controller;
import control.Observer;
import events.Event;
import exceptions.SimulationError;
import model.map.RoadMap;
import utils.Images;


@SuppressWarnings("serial")
public class ToolBar extends JToolBar
					 implements Observer{

	
	private JSpinner pasos; // pasos que debera dar el simulador
	private JTextField time; // tiempo en el que va la simulacion
	private JSpinner delay;
	
	
	private List<JComponent> listaComponentes;
	
	public ToolBar(MainView mainWindow, Controller controller) {
		super(); // = new ToolBar();
		
		controller.addObserver(this);
		listaComponentes = new ArrayList<JComponent>();
		
		IconoCargarArchivo(mainWindow);
		IconoGuardarArchivo(mainWindow);
		IconoLimpiarEventos(mainWindow);
		this.addSeparator();
		
		IconoCargarEventos(mainWindow, controller);
		IconoEjecutarSimulador(mainWindow);
		IconoDetenerSimulador(mainWindow);
		IconoReiniciarSimulador(controller);
		this.addSeparator();
		
		SpinnerDelay();
		this.addSeparator();
		
		SpinnerPasosAEjecutar();
		this.addSeparator();
		
		TextFieldTiempo();
		this.addSeparator();
		
		IconoGenerarInformes(mainWindow);
		IconoLimpiarInformes(mainWindow);
		IconoGuardarInformes(mainWindow);
		this.addSeparator();
		
		IconoSalir(mainWindow);
	}
	
	// ICONO CARGAR ARCHIVO DE TEXTO
	//------------------------------------------------------------------------------
	private void IconoCargarArchivo(MainView mainWindow) {
		
		JButton botonCargarTexto = new JButton();
		botonCargarTexto.setToolTipText("Loads an events file");
		botonCargarTexto.setIcon(new ImageIcon(
								 Images.loadImage("resources/icons/open.png")));
		
		botonCargarTexto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.loadEventsFile();
			}
		});
		
		this.add(botonCargarTexto);
		listaComponentes.add(botonCargarTexto);
	}
	
	
	// ICONO GUARDAR ARCHIVO DE TEXTO
	//------------------------------------------------------------------------------
	private void IconoGuardarArchivo(MainView mainWindow) {
		
		JButton botonGuardarEventos = new JButton();
		botonGuardarEventos.setToolTipText("Save events");
		botonGuardarEventos.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/save.png")));
		
		botonGuardarEventos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.salvaEventos();
			}
		});
		
		this.add(botonGuardarEventos);
		listaComponentes.add(botonGuardarEventos);
	}
	
	// ICONO LIMPIAR EDITOR DE TEXTO
	//------------------------------------------------------------------------------ 
	private void IconoLimpiarEventos(MainView mainWindow) {
		
		JButton botonLimpiarEventos = new JButton();
		botonLimpiarEventos.setToolTipText("Clear the events editor");
		botonLimpiarEventos.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/clear.png")));
		
		botonLimpiarEventos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.clearAreaEventos();
				
			}
		});
		
		this.add(botonLimpiarEventos);
		listaComponentes.add(botonLimpiarEventos);
	}
	
	
	// ICONO CARGAR LOS EVENTOS AL SIMULADOR
	//------------------------------------------------------------------------------
	private void IconoCargarEventos(MainView mainWindow,
										 Controller controller) {
		
		JButton botonCargarEventos = new JButton();
		botonCargarEventos.setToolTipText("Load the events into the simulator");
		botonCargarEventos.setIcon(new ImageIcon(
				                   Images.loadImage("resources/icons/events.png")));
		
		botonCargarEventos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					 // Se reinicia el simulador cada vez que se cargan los eventos
					 controller.reset();
					 byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
					 controller.loadEvents(new ByteArrayInputStream(contenido));
					 
			} catch (SimulationError err) { }
				
			mainWindow.setMensaje("Events loaded to the simulator!");
		}
		
		});
		this.add(botonCargarEventos);
		listaComponentes.add(botonCargarEventos);
		
	}
	
	// ICONO EJECUTAR EL SIMULADOR
	//------------------------------------------------------------------------------
	
	private void IconoEjecutarSimulador(MainView mainWindow) {
		
		JButton botonEjecutar = new JButton();
		botonEjecutar.setToolTipText("Execute the simulator");
		botonEjecutar.setIcon(new ImageIcon(
								  Images.loadImage("resources/icons/play.png")));
		
		
		
		botonEjecutar.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.ejecutarSimulador();
			}		
		});
		this.add(botonEjecutar);
		listaComponentes.add(botonEjecutar);
	}
	
	// ICONO PARA DETENER EL SIMULADOR
	//------------------------------------------------------------------------------
	private void IconoDetenerSimulador(MainView mainWindow) {
		
		JButton botonDetener= new JButton();
		botonDetener.setToolTipText("Stops the simulator");
		botonDetener.setIcon(new ImageIcon(
							       Images.loadImage("resources/icons/stop.png")));
		
		botonDetener.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.detenerSimulador();
			}
		});
		
		this.add(botonDetener);
			
	}
	
	
	
	// ICONO REINICIAR EL SIMULADOR
	//------------------------------------------------------------------------------
	
	private void IconoReiniciarSimulador(Controller controller) {
		
		JButton botonReiniciar = new JButton();
		botonReiniciar.setToolTipText("Resets the simulator");
		botonReiniciar.setIcon(new ImageIcon(
							       Images.loadImage("resources/icons/reset.png")));
		
		botonReiniciar.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.reset();
				
			}
		});
		
		this.add(botonReiniciar);
		listaComponentes.add(botonReiniciar);
			
	}
	
	// SPINNER DELAY
	//------------------------------------------------------------------------------
	private void SpinnerDelay() {
		
		this.add(new JLabel(" Delay: "));
		
		delay = new JSpinner(new SpinnerNumberModel(500,0,1000000,100));
		
		delay.setToolTipText("Delay");
		delay.setMaximumSize(new Dimension(70,70));
		delay.setMinimumSize(new Dimension(70,70));
		
		
		this.add(delay);
		listaComponentes.add(delay);
	}
	
	
	
	// SPINNER CON EL NUMERO DE PASOS A EJECUTAR DE UNA VEZ EN EL SIMULADOR
	//------------------------------------------------------------------------------
	
	private void SpinnerPasosAEjecutar() {
		
		this.add(new JLabel(" Steps: "));
		
		pasos = new JSpinner(new SpinnerNumberModel(10,1,1000,1));
		
		pasos.setToolTipText("Steps to execute: 1-1000");
		pasos.setMaximumSize(new Dimension(70,70));
		pasos.setMinimumSize(new Dimension(70,70));
		
		
		this.add(pasos);
		listaComponentes.add(pasos);
	}
	
	// TEXTFIELD CON EL TIEMPO ACTUAL DE LA SIMULACION
	//------------------------------------------------------------------------------
	
	private void TextFieldTiempo() {
		
		this.add(new JLabel(" Time: "));
		
		time = new JTextField("0",5);
		time.setToolTipText("Current Time");
		time.setMaximumSize(new Dimension(70,70));
		time.setMinimumSize(new Dimension(70,70));
		time.setEditable(false);
		
		this.add(time);
		
	}
	
	// ICONO GENERAR INFORMES
    //------------------------------------------------------------------------------
	
	private void IconoGenerarInformes(MainView mainWindow){
		
		
		JButton botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Generate Reports");
		botonGeneraReports.setIcon(new ImageIcon(
								       Images.loadImage("resources/icons/report.png")));
		
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.mostrarDialofgoInformes();
			}
		});
		this.add(botonGeneraReports);	
		listaComponentes.add(botonGeneraReports);
	}
	
	// ICONO LIMPIAR AREA DE INFORMES
    //-------------------------------------------------------------------------------
	
	private void IconoLimpiarInformes(MainView mainWindow) {
		

		JButton botonLimpiarInformes = new JButton();
		botonLimpiarInformes.setToolTipText("Clear Reports Area");
		botonLimpiarInformes.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/delete_report.png")));
		
		botonLimpiarInformes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.clearReportsArea();
				
			}
		});
		
		this.add(botonLimpiarInformes);
		listaComponentes.add(botonLimpiarInformes);
	}
	
	// ICONO PARA GUARDAR LOS INFORMES
    //-------------------------------------------------------------------------------
	
	private void IconoGuardarInformes(MainView mainWindow) {
		
		JButton botonGuardarInformes = new JButton();
		botonGuardarInformes.setToolTipText("Save Reports");
		botonGuardarInformes.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/save_report.png")));
		
		botonGuardarInformes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.salvaInformes();
			}
		});
		
		this.add(botonGuardarInformes);
		listaComponentes.add(botonGuardarInformes);
		
		
	}
	

	// ICONO PARA SALIR
    //-------------------------------------------------------------------------------
	
	private void IconoSalir(MainView mainwindow) {
		
		JButton botonSalir = new JButton();
		botonSalir.setToolTipText("Exit");
		botonSalir.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/exit.png")));
		
		botonSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainwindow.exit();
				
			}
		});
		this.add(botonSalir);
		listaComponentes.add(botonSalir);
	}
	
	// Devuelve los pasos de la simulacion
	public int getPasosSpinner() {
		return (int) pasos.getValue();
		
	}
	
	// Devuelve el delay
	public int getDelay() {
		return (int) delay.getValue();
			
	}
	
	// Devuelve el tiempo de la simulacion
	public int getTiempo() {
		return Integer.parseInt(time.getText());
	}
	
	// Inabilitar componentes barra de herramientas
	public void disabled() {
		for(JComponent comp: listaComponentes) {
			comp.setEnabled(false);
		}
	}
	
	// Inabilitar componentes barra de herramientas
	public void enabled() {
		for(JComponent comp: listaComponentes) {
			comp.setEnabled(true);
		}
	}
	
	// OBSERVADORES
	// -----------------------------------------------------------------------------
	@Override
	public void simulatorError(int tiempo, RoadMap map, List<Event> events,
			SimulationError e) { }

	@Override
	public void advance(int tiempo, RoadMap mapa, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				time.setText(""+ tiempo);
			}
		});
		
	}

	@Override
	public void addEvent(int tiempo, RoadMap mapa, List<Event> events) { }

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		time.setText("0");	
	}
}
