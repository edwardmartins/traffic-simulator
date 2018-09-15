package events.constructors;

import events.Event;
import ini.IniSection;

public abstract class EventsConstructor {

	protected String label;  // indicates the type of event
	protected String[] keys; // keys to indicate the fields of the events
	protected String[] defaultValues; 
	
	EventsConstructor() {
		this.label = null;
		this.keys = null;
		this.defaultValues = null;
	}

	// Parse the attributes of the events
	public abstract Event parser(IniSection section);
	
	// Template for the event
	public abstract String template();

	// Check if the id is valid
	protected static String checkId(IniSection section, String key) {
		String s = section.getValue(key);
		
		if (!idIsValid(s))
			throw new IllegalArgumentException("The value " + s + " for " + key
											 + " is not valid");
		else
			return s;
	}

	private static boolean idIsValid(String id) {
		return id != null && id.matches("[a-z0-9_]+");
	}

	// Methods to parser positive and negative integers
	//----------------------------------------------------------------------------------------
	
	protected static int parseInt(IniSection section, String key) {
		String v = section.getValue(key);
		
		if (v == null)
			throw new IllegalArgumentException("Inexist valur for the key: " + key);
		else
			return Integer.parseInt(section.getValue(key));
	}

	// Allows to put a default value besides parse the integer
	protected static int parseInt(IniSection section, String key, int defaultValue) {
		String v = section.getValue(key);
		
		return (v != null) ? Integer.parseInt(section.getValue(key)) : defaultValue;
	}
	
	protected static int parseNegativeInt(IniSection section, String key, int defaultValue) {
		int i = EventsConstructor.parseInt(section, key, defaultValue);
		
		if (i < 0)
			throw new IllegalArgumentException("The value " + i + " for the " + key
												+ " is not a valid ID");
		else
			return i;
	}

}
