package events.constructors;

import events.Event;
import events.NewCircularJunctionEvent;
import ini.IniSection;

public class CircularJunctionEventConstructor extends JunctionEventConstructor {
	
	public CircularJunctionEventConstructor() {
		this.label = "new_junction";
		this.keys = new String[] { "time", "id", "type", "max_time_slice", "min_time_slice" }; 
		this.defaultValues = new String[] { "", "", "rr", "", "" };
	}
		
	@Override
	public Event parser(IniSection section) {
		if (!section.getTag().equals(this.label) || !section.getValue("type").equals("rr"))
			return null;
		else
			return new NewCircularJunctionEvent(
				EventsConstructor.parseNegativeInt(section, "time", 0),
				EventsConstructor.checkId(section, "id"),
				EventsConstructor.parseInt(section,"min_time_slice"),
				EventsConstructor.parseInt(section,"max_time_slice"));
	}
	
	public String toString() {
		 return "New Circular Junction";
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
