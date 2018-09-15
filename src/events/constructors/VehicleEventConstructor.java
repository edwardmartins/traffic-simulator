package events.constructors;

import events.Event;
import events.NewVehicleEvent;
import ini.IniSection;

public class VehicleEventConstructor extends EventsConstructor {
	
	public VehicleEventConstructor() {
		this.label = "new_vehicle";
		this.keys = new String[] { "time", "id", "itinerary", "max_speed" };	
		this.defaultValues = new String[] { "", "", "","" };
	}

	@Override
	public Event parser(IniSection section) {
		
		if (!section.getTag().equals(this.label) || section.getValue("type") != null)
			return null;
		else
			return new NewVehicleEvent(
			EventsConstructor.parseNegativeInt(section, "time", 0),
			EventsConstructor.checkId(section, "id"),
			EventsConstructor.parseInt(section, "max_speed"),
			section.getValue("itinerary").split(",")
		);

	}
	
	public String toString() {
		 return "New Vehicle";
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
