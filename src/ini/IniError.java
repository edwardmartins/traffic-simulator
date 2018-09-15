package ini;

public class IniError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	IniError(String msg) {
		super(msg);
	}
}
