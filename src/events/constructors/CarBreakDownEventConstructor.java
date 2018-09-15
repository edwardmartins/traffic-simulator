package events.constructors;

import events.Event;
import ini.IniSection;
import events.CarBreakDownEvent;

public class CarBreakDownEventConstructor extends EventsConstructor {

	public CarBreakDownEventConstructor() {
		this.label = "make_vehicle_faulty";
		this.keys = new String[] {"time", "vehicles","duration"};
		this.defaultValues = new String[] { "", "", "",};
	}
	
	@Override
	public Event parser(IniSection section) {
	
		if (!section.getTag().equals(this.label) || section.getValue("type") != null)
			return null;
		else
			return new CarBreakDownEvent(
			EventsConstructor.parseNegativeInt(section, "time", 0),
			section.getValue("vehicles").split(","), 
			EventsConstructor.parseInt(section, "duration")
		);
	}
	
	public String toString() {
		 return "New Vehicle BreakDown";
	}

	@Override
	public String template() {
		String result = "[" + label + "]\n";
		
		for(int i = 0; i < keys.length; i++) {
			result += keys[i] + " = " + defaultValues[i] + "\n";
		}
		
		return result;
		
	}
	
	
	
}
