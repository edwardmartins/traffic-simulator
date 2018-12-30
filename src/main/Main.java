package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import control.Controller;
import model.simulator.TrafficSimulator;
import view.MainView;

// Introduce -h as an argument to see help
public class Main {

	private final static Integer defaultTimeLimit = 10;
	private static Integer timeLimit = null;
	private static String inputFile = null;
	private static String outputFile = null;
	private static ExecutionMode mode = null;
	
	private enum ExecutionMode {
		BATCH("batch"), GUI("gui");

		private String modeDescription;

		private ExecutionMode(String modeDesc) {
			modeDescription = modeDesc;
		}

		private String getModeDescription() {
			return modeDescription;
		}
	}

	private static void argumentParser(String[] args) {

		// Define the valid command line options
		Options commandLineOptions = Main.buildOptions();

		// Parse the command line as provided in args
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine line = parser.parse(commandLineOptions, args);
			
			// Parse the different options
			helpOptionParse(line, commandLineOptions);
			modeOptionParse(line);
			inputFileParse(line);
			outputFileParse(line);
			stepsOptionParse(line);
			
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			String[] rest = line.getArgs();
			if (rest.length > 0) {
				String error = "Illegal arguments:";
				for (String o : rest)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	// Build Options
	private static Options buildOptions() {
		Options commandOptions = new Options();

		// h
		commandOptions.addOption(Option.builder("h").longOpt("help")
				.desc("Shows the help message.").build());

		// i
		commandOptions
				.addOption(Option.builder("i").longOpt("input").hasArg()
						.desc("InputFile with the events.").build());
		// o
		commandOptions.addOption(Option.builder("o").longOpt("output").hasArg()
				.desc("OutputFile where the results are written.").build());
		// t
		commandOptions.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Number of steps that the simulator executes "
						+ "(Default value is " + Main.defaultTimeLimit + ").")
				.build());
		// m
		commandOptions.addOption(Option.builder("m").longOpt("mode").hasArg()
				.desc("'batch' for batch mode "
						+ "and 'gui' for GUI mode (default value is 'batch').").build());

		return commandOptions;
	}

	
	private static void helpOptionParse(CommandLine line, Options options) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), options, true);
			System.exit(0);
		}
	}
	
	
	private static void inputFileParse(CommandLine line) throws ParseException {
		Main.inputFile = line.getOptionValue("i");
		if (Main.inputFile == null && mode.equals(ExecutionMode.BATCH)) {
			throw new ParseException("InputFile doesn't exist");
		}
	}
	
	
	private static void outputFileParse(CommandLine line) throws ParseException {
		if (mode.equals(ExecutionMode.BATCH))
			Main.outputFile = line.getOptionValue("o");
	}
	
	
	private static void stepsOptionParse(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", Main.defaultTimeLimit.toString());
		
		try {
			Main.timeLimit = Integer.parseInt(t);
			assert (Main.timeLimit < 0);
		} catch (Exception e) {
			throw new ParseException("Invalid time limit");
		}
	}
	
	
	private static void modeOptionParse(CommandLine line) throws ParseException {
		String modo = line.getOptionValue("m");

		if (modo == null || modo.equals(ExecutionMode.BATCH.getModeDescription()))
			Main.mode = ExecutionMode.BATCH;
		else {
			Main.mode = ExecutionMode.GUI;
		}
	}
	
	
	// Main
	public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
		Main.argumentParser(args);

		if (mode.equals(ExecutionMode.BATCH))
			Main.startConsoleMode();
		else
			Main.startsGraphicalMode();
	}

	// Console Mode
	private static void startConsoleMode() throws IOException {

		InputStream is = new FileInputStream(new File(Main.inputFile));
		OutputStream os = Main.outputFile == null ? System.out : new FileOutputStream(new File(Main.outputFile));

		TrafficSimulator sim = new TrafficSimulator();
		Controller ctrl = new Controller(sim, Main.timeLimit, is, os);
		ctrl.execute();

		is.close();
		System.out.println("Done!");
		os.close();
	}

	// Graphical Mode
	private static void startsGraphicalMode()
			throws FileNotFoundException, InvocationTargetException, InterruptedException {

		TrafficSimulator sim = new TrafficSimulator();
		OutputStream os = Main.outputFile == null ? System.out : new FileOutputStream(new File(Main.outputFile));
		Controller ctrl = new Controller(sim, Main.timeLimit, null, os);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new MainView(Main.inputFile, ctrl);

				} catch (FileNotFoundException e) {
					System.out.println("InputFile not found");
				}
			}
		});
	}

}
