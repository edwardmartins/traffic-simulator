package exceptions;

@SuppressWarnings("serial")
public class SimulationError extends Exception {

	public SimulationError() {
		super();
	}
	
	public SimulationError(String message) {
		super(message);
	}	
}
