package events.constructors;

import events.Event;
import events.NewJunctionEvent;
import ini.IniSection;

public class JunctionEventConstructor extends EventsConstructor {

	public JunctionEventConstructor() {
		this.label = "new_junction";
		this.keys = new String[] { "time", "id" }; 
		this.defaultValues = new String[] { "", "", };
	}

	@Override
	public Event parser(IniSection section) {
		if (!section.getTag().equals(this.label) || section.getValue("type") != null)
			return null;
		else
			return new NewJunctionEvent(
				EventsConstructor.parseNegativeInt(section, "time", 0),
				EventsConstructor.checkId(section, "id"));
	}
	
	public String toString() {
		 return "New Junction";
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
