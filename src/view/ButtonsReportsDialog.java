package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.objects.Road;
import model.objects.GenericJunction;
import model.objects.Vehicle;

@SuppressWarnings("serial")
public class ButtonsReportsDialog extends JPanel {

	public ButtonsReportsDialog(ReportsDialog reportsDialog, MainView mainWindow) {

		this.setLayout(new FlowLayout());

		JButton generate = new JButton("Generate");
		generate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				List<Vehicle> vehiclesList = reportsDialog.getSelectedVehicles();
				List<Road> roadsList = reportsDialog.getSelectedRoads();
				List<GenericJunction<?>> junctionList = reportsDialog.getSelectedJunctions();
				
				mainWindow.clearReportsArea();
				
				mainWindow.insertInReportsArea(generateSpecificReport(junctionList,
						vehiclesList,
						roadsList, 
						mainWindow.getSimulationTime()));
			}
		});

		this.add(generate);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				reportsDialog.hideDialog();
				

			}
		});

		this.add(generate);
		this.add(cancel);

	}

	public String generateSpecificReport(List<GenericJunction<?>> JunctionList,
										   List<Vehicle> vehiclesList,
			                               List<Road> roadsList, int time) {

		String report = "";
	
		for (GenericJunction<?> junction : JunctionList)
			report += junction.generateInform(time) + "\n";

	
		for (Road road : roadsList) {
			report += road.generateInform(time) + "\n";
		}
		
		for (Vehicle v : vehiclesList) {
			report += v.generateInform(time) + "\n";
		}

		return report;

	}

}
