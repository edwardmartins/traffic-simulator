package events.constructors;

import events.Event;
import events.NewCongestedJunctionEvent;
import ini.IniSection;

public class CongestedJunctionEventConstructor extends JunctionEventConstructor {
	
	public CongestedJunctionEventConstructor() {
		this.label = "new_junction";
		this.keys = new String[] { "time", "id", "type"}; 
		this.defaultValues = new String[] { "", "", "mc"};
	}
	
	public Event parser(IniSection section) {
		if (!section.getTag().equals(this.label) || !section.getValue("type").equals("mc"))
			return null;
		else
			return new NewCongestedJunctionEvent(
				EventsConstructor.parseNegativeInt(section, "time", 0),
				EventsConstructor.checkId(section, "id"));
	}
	
	public String toString() {
		 return "New Congested Junction";
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
