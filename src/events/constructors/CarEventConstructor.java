package events.constructors;

import events.Event;
import events.NewCarEvent;
import ini.IniSection;

public class CarEventConstructor extends VehicleEventConstructor {
	
	public CarEventConstructor() {
		this.label = "new_vehicle";
		this.keys = new String[] { "time", "id", "itinerary",
						"max_speed","type","resistance","fault_probability",
						"max_fault_duration","seed" };	
		this.defaultValues = new String[] { "", "", "","","car","","","",""};
	}
	
	@Override
	public Event parser(IniSection section) {
		
		if (!section.getTag().equals(this.label) || !section.getValue("type").equals("car"))
			return null;
		else
			return new NewCarEvent(
			EventsConstructor.parseNegativeInt(section, "time", 0),
			EventsConstructor.checkId(section, "id"),
			EventsConstructor.parseInt(section, "max_speed"),
			section.getValue("itinerary").split(","),
			EventsConstructor.parseInt(section,"resistance"),
			Double.parseDouble(section.getValue("fault_probability")),
			EventsConstructor.parseInt(section,"max_fault_duration"),
			Long.parseLong(section.getValue("seed")));
	}
	
	public String toString() {
		 return "New Car";
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
