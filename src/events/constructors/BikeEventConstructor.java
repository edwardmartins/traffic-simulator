package events.constructors;

import events.Event;
import events.NewBikeEvent;
import ini.IniSection;

public class BikeEventConstructor extends VehicleEventConstructor {
	
	public BikeEventConstructor() {
		this.label = "new_vehicle";
		this.keys = new String[] { "time", "id", "itinerary",
						"max_speed","type",};
		
		this.defaultValues = new String[] { "", "", "","","bike"};
	}

	@Override
	public Event parser(IniSection section) {
		
		if (!section.getTag().equals(this.label) || !section.getValue("type").equals("bike"))
			return null;
		else
			return new NewBikeEvent(
			EventsConstructor.parseNegativeInt(section, "time", 0),
			EventsConstructor.checkId(section, "id"),
			EventsConstructor.parseInt(section, "max_speed"),
			section.getValue("itinerary").split(",")
		);

	}
	
	public String toString() {
		 return "New Bike";
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
