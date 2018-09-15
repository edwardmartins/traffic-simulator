package events.constructors;

import events.Event;
import events.NewHighwayEvent;
import ini.IniSection;

public class HighwayEventConstructor extends RoadEventConstructor{
	
	public HighwayEventConstructor() {
		this.label = "new_road";
		this.keys = new String[] { "time", "id", "src",
									"dest","max_speed","length","type","lanes"};
		
		this.defaultValues = new String[] { "", "", "", "", "", "","lanes","" };
	}
	
	@Override
	public Event parser(IniSection section) {
	
		if (!section.getTag().equals(this.label) || !section.getValue("type").equals("lanes"))
			return null;
		else
			return new NewHighwayEvent(
					EventsConstructor.parseNegativeInt(section, "time", 0),
					EventsConstructor.checkId(section, "id"),
					section.getValue("src"),
					section.getValue("dest"),
					EventsConstructor.parseInt(section, "max_speed"),
					EventsConstructor.parseInt(section, "length"),
					EventsConstructor.parseInt(section, "lanes")
			);
	}
	
	public String toString() {
		 return "New Highway";
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
