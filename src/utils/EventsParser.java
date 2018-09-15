package utils;

import events.*;
import events.constructors.CarBreakDownEventConstructor;
import events.constructors.HighwayEventConstructor;
import events.constructors.BikeEventConstructor;
import events.constructors.RoadEventConstructor;
import events.constructors.PathEventConstructor;
import events.constructors.CarEventConstructor;
import events.constructors.JunctionEventConstructor;
import events.constructors.CircularJunctionEventConstructor;
import events.constructors.CongestedJunctionEventConstructor;
import events.constructors.VehicleEventConstructor;
import ini.IniSection;
import events.constructors.EventsConstructor;

public class EventsParser {

	// Array with the events
	private static EventsConstructor[] eventsConstructorsArray = {
			new JunctionEventConstructor(),
			new RoadEventConstructor(), 
			new VehicleEventConstructor(),
			new CarBreakDownEventConstructor(),
			new HighwayEventConstructor(),
			new PathEventConstructor(),
			new BikeEventConstructor(),
			new CarEventConstructor(),
			new CircularJunctionEventConstructor(),
			new CongestedJunctionEventConstructor()
	};

	public static Event parseEvent(IniSection sec) {
		int i = 0;
		boolean keepGoing = true;
		Event e = null;
		
		while (i < EventsParser.eventsConstructorsArray.length && keepGoing) {
			
			e = EventsParser.eventsConstructorsArray[i].parser(sec);
			
			if (e != null)
				keepGoing = false;
			else
				i++;
		}
		return e;
	}

	public static EventsConstructor[] getEventsConstructorArray() {
		return eventsConstructorsArray;
	}
	

}
