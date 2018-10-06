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

	
	private JSpinner simulatorSteps; 
	private JTextField time; 
	private JSpinner delay;
	
	
	private List<JComponent> componentsList;
	
	public ToolBar(MainView mainWindow, Controller controller) {
		super(); // = new ToolBar();
		
		controller.addObserver(this);
		componentsList = new ArrayList<JComponent>();
		
		loadFileIcon(mainWindow);
		saveFileIcon(mainWindow);
		clearEventsIcon(mainWindow);
		this.addSeparator();
		
		loadEventsIntoSimulatorIcon(mainWindow, controller);
		executeSimulatorIcon(mainWindow);
		stopSimulatorIcon(mainWindow);
		resetSimulatorIcon(controller);
		this.addSeparator();
		
		SpinnerDelay();
		this.addSeparator();
		
		simulatorStepsSpinner();
		this.addSeparator();
		
		textFieldTime();
		this.addSeparator();
		
		generateReportsIcon(mainWindow);
		clearReportsIcon(mainWindow);
		saveReportsIcon(mainWindow);
		this.addSeparator();
		
		exitIcon(mainWindow);
	}
	
	// LOAD FILE ICON
	//------------------------------------------------------------------------------
	private void loadFileIcon(MainView mainWindow) {
		
		JButton loadFileButton = new JButton();
		loadFileButton.setToolTipText("Loads an events file");
		loadFileButton.setIcon(new ImageIcon(
								 Images.loadImage("resources/icons/open.png")));
		
		loadFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.loadEventsFile();
			}
		});
		
		this.add(loadFileButton);
		componentsList.add(loadFileButton);
	}
	
	
	// SAVE FILE ICON
	//------------------------------------------------------------------------------
	private void saveFileIcon(MainView mainWindow) {
		
		JButton saveFileButton = new JButton();
		saveFileButton.setToolTipText("Save an events file");
		saveFileButton.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/save.png")));
		
		saveFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.saveEvents();
			}
		});
		
		this.add(saveFileButton);
		componentsList.add(saveFileButton);
	}
	
	// CLEAR EVENTS ICON
	//------------------------------------------------------------------------------ 
	private void clearEventsIcon(MainView mainWindow) {
		
		JButton clearEventsButton = new JButton();
		clearEventsButton.setToolTipText("Clear events");
		clearEventsButton.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/clear.png")));
		
		clearEventsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.clearEventsArea();
				
			}
		});
		
		this.add(clearEventsButton);
		componentsList.add(clearEventsButton);
	}
	
	
	// LOAD EVENTS INTO SIMULATOR ICON
	//------------------------------------------------------------------------------
	private void loadEventsIntoSimulatorIcon(MainView mainWindow,
										 Controller controller) {
		
		JButton loadEventsIcon = new JButton();
		loadEventsIcon.setToolTipText("Load events into the simulator");
		loadEventsIcon.setIcon(new ImageIcon(
				                   Images.loadImage("resources/icons/events.png")));
		
		loadEventsIcon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					 controller.reset();
					 byte[] content = mainWindow.getEventsEditorText().getBytes();
					 controller.loadEvents(new ByteArrayInputStream(content));
					 
			} catch (SimulationError err) { }
				
			mainWindow.setMensaje("Events loaded to the simulator!");
		}
		
		});
		this.add(loadEventsIcon);
		componentsList.add(loadEventsIcon);
		
	}
	
	// EXECUTE SIMULATOR ICON
	//------------------------------------------------------------------------------
	
	private void executeSimulatorIcon(MainView mainWindow) {
		
		JButton executeButton = new JButton();
		executeButton.setToolTipText("Execute the simulator");
		executeButton.setIcon(new ImageIcon(
								  Images.loadImage("resources/icons/play.png")));
		
		
		
		executeButton.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.executeSimulator();
			}		
		});
		this.add(executeButton);
		componentsList.add(executeButton);
	}
	
	// STOP SIMULATOR ICON
	//------------------------------------------------------------------------------
	private void stopSimulatorIcon(MainView mainWindow) {
		
		JButton stopButton= new JButton();
		stopButton.setToolTipText("Stops the simulator");
		stopButton.setIcon(new ImageIcon(
							       Images.loadImage("resources/icons/stop.png")));
		
		stopButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.stopSimulator();
			}
		});
		
		this.add(stopButton);
			
	}
	
	
	
	// RESET SIMULATOR ICON
	//------------------------------------------------------------------------------
	
	private void resetSimulatorIcon(Controller controller) {
		
		JButton resetButton = new JButton();
		resetButton.setToolTipText("Resets the simulator");
		resetButton.setIcon(new ImageIcon(
							       Images.loadImage("resources/icons/reset.png")));
		
		resetButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				controller.reset();
				
			}
		});
		
		this.add(resetButton);
		componentsList.add(resetButton);
			
	}
	
	// DELAY SPINNER
	//------------------------------------------------------------------------------
	private void SpinnerDelay() {
		
		this.add(new JLabel(" Delay: "));
		
		delay = new JSpinner(new SpinnerNumberModel(500,0,1000000,100));
		
		delay.setToolTipText("Delay");
		delay.setMaximumSize(new Dimension(70,70));
		delay.setMinimumSize(new Dimension(70,70));
		
		
		this.add(delay);
		componentsList.add(delay);
	}
	
	
	
	// SIMULATOR STEPS SPINNER
	//------------------------------------------------------------------------------
	
	private void simulatorStepsSpinner() {
		
		this.add(new JLabel(" Steps: "));
		
		simulatorSteps = new JSpinner(new SpinnerNumberModel(10,1,1000,1));
		
		simulatorSteps.setToolTipText("Steps to execute: 1-1000");
		simulatorSteps.setMaximumSize(new Dimension(70,70));
		simulatorSteps.setMinimumSize(new Dimension(70,70));
		
		
		this.add(simulatorSteps);
		componentsList.add(simulatorSteps);
	}
	
	// TEXTFIELD THAT INDICATES THE TIME OF THE SIMULATION
	//------------------------------------------------------------------------------
	
	private void textFieldTime() {
		
		this.add(new JLabel(" Time: "));
		
		time = new JTextField("0",5);
		time.setToolTipText("Current Time");
		time.setMaximumSize(new Dimension(70,70));
		time.setMinimumSize(new Dimension(70,70));
		time.setEditable(false);
		
		this.add(time);
		
	}
	
	// GENERATE REPORTS ICON
    //------------------------------------------------------------------------------
	
	private void generateReportsIcon(MainView mainWindow){
		
		
		JButton generateReportsButton = new JButton();
		generateReportsButton.setToolTipText("Generate Reports");
		generateReportsButton.setIcon(new ImageIcon(
								       Images.loadImage("resources/icons/report.png")));
		
		generateReportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.showReportsDialog();
			}
		});
		this.add(generateReportsButton);	
		componentsList.add(generateReportsButton);
	}
	
	// CLEAR REPORTS ICON
    //-------------------------------------------------------------------------------
	
	private void clearReportsIcon(MainView mainWindow) {
		

		JButton clearReportsButton = new JButton();
		clearReportsButton.setToolTipText("Clear Reports Area");
		clearReportsButton.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/delete_report.png")));
		
		clearReportsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.clearReportsArea();
				
			}
		});
		
		this.add(clearReportsButton);
		componentsList.add(clearReportsButton);
	}
	
	// SAVE REPORTS ICON
    //-------------------------------------------------------------------------------
	
	private void saveReportsIcon(MainView mainWindow) {
		
		JButton saveReportsButton = new JButton();
		saveReportsButton.setToolTipText("Save Reports");
		saveReportsButton.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/save_report.png")));
		
		saveReportsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainWindow.saveReports();
			}
		});
		
		this.add(saveReportsButton);
		componentsList.add(saveReportsButton);
		
		
	}
	

	// EXIT ICON
    //-------------------------------------------------------------------------------
	
	private void exitIcon(MainView mainwindow) {
		
		JButton exitButton = new JButton();
		exitButton.setToolTipText("Exit");
		exitButton.setIcon(new ImageIcon(
							 Images.loadImage("resources/icons/exit.png")));
		
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				mainwindow.exit();
				
			}
		});
		this.add(exitButton);
		componentsList.add(exitButton);
	}
	
	
	public int getSimulatorSteps() {
		return (int) simulatorSteps.getValue();
		
	}
	
	
	public int getDelay() {
		return (int) delay.getValue();
			
	}
	
	
	public int getSimulationTime() {
		return Integer.parseInt(time.getText());
	}
	
	
	public void disableToolbarComponents() {
		for(JComponent comp: componentsList) {
			comp.setEnabled(false);
		}
	}
	
	
	public void enableToolbarComponents() {
		for(JComponent comp: componentsList) {
			comp.setEnabled(true);
		}
	}
	
	// OBSERVERS
	// -----------------------------------------------------------------------------
	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events,
			SimulationError e) { }

	@Override
	public void advance(int tim, RoadMap mapa, List<Event> events) {
		
		SwingUtilities.invokeLater(new Runnable() {	
			@Override
			public void run() {
				time.setText(""+ tim);
			}
		});
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) { }

	@Override
	public void reset(int tiempo, RoadMap mapa, List<Event> events) {
		time.setText("0");	
	}
}
