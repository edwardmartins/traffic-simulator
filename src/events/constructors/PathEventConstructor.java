package events.constructors;

import events.Event;
import events.NewPathEvent;
import ini.IniSection;

public class PathEventConstructor extends RoadEventConstructor {
	
	
	public PathEventConstructor() {
		this.label = "new_road";
		this.keys = new String[] { "time", "id", "src",
									"dest","max_speed","length","type"};
		
		this.defaultValues = new String[] { "", "", "", "", "", "","dirt" };
	}

	@Override
	public Event parser(IniSection section) {
		
		if (!section.getTag().equals(this.label) || !section.getValue("type").equals("dirt"))  
			return null;
		else
			return new NewPathEvent(
					EventsConstructor.parseNegativeInt(section, "time", 0),
					EventsConstructor.checkId(section, "id"),
					section.getValue("src"),
					section.getValue("dest"),
					EventsConstructor.parseInt(section, "max_speed"),
					EventsConstructor.parseInt(section, "length")
			);
	}
	
	public String toString() {
		 return "New Path";
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
