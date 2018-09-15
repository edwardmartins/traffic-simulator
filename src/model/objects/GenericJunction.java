package model.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exceptions.SimulationError;
import ini.IniSection;


// Generic Class to use two kind of entry roads, simple entry roads and entry roads with an interval
abstract public class GenericJunction<T extends EntryRoad> extends SimulationObject { 

	protected int greenLightIndex; 
	protected List<T> entryRoadsList; 
	protected Map <String, T> entryRoadsMap;
	protected Map <GenericJunction<?>, Road> outgoingRoads;
	
	
	public GenericJunction(String id) {
		super(id);
		greenLightIndex = -1; // every light is red at the beggining
		entryRoadsList = new ArrayList<T>();
		entryRoadsMap = new HashMap<>();
		outgoingRoads = new HashMap<>();
	}
	
	// Returns the road that arrives to junction from "this"
	public Road getRoadToJunction(GenericJunction<?> junction) {
		return outgoingRoads.get(junction);
	}

	
	public void addEntryRoad(String roadId, Road road) {
		T entryRoad =  createEntryRoad(road); 
		entryRoadsList.add(entryRoad);
		entryRoadsMap.put(roadId, entryRoad);
	}
	
	
	abstract protected T createEntryRoad(Road road);

	
	public void addOutgoingRoad(GenericJunction<?> destination, Road road) {
		outgoingRoads.put(destination, road);
	}

	// Add the vehicle to the queue of vehicles of the corresponding entry road(roadId)
	public void enterVehicleToJunction(String roadId, Vehicle vehicle) {
		EntryRoad entryRoad = entryRoadsMap.get(roadId);
		entryRoad.addToVehiclesQueue(vehicle);
	}

	
	abstract protected void updateTrafficLights();
	

	void putRedLight(int index) {
		entryRoadsList.get(index).putTrafficLight(false);;
	}
	
	
	void putGreenLight(int index) {
		entryRoadsList.get(index).putTrafficLight(true);
	}
	
	
	protected T getEntryRoadWithGreenLight(){
		return entryRoadsList.get(greenLightIndex);
	}

	
	@Override
	public void advance() throws SimulationError {
		if(entryRoadsList.size() > 0) {
			if(greenLightIndex != -1) { 
				entryRoadsList.get(greenLightIndex).advanceFirstVehicle();
			}
			updateTrafficLights();
		}
	}

	
	@Override
	protected String getSectionName() {
		return "junction_report";
	}
	
	
	public List<T> getEntryRoadsList() {
		return entryRoadsList;
	}
	
	
	public String entryRoadsWithGreenLights() {
		String result = "||  ";
		int cont = 1;
		
		if(entryRoadsList.size() > 0) {
			for(int i = 0; i < entryRoadsList.size(); i++) {
				if(entryRoadsList.get(i).hasGreenLight()) {
					result += entryRoadsList.get(i);
					
					cont++;
					if(cont > 1)
						result += "  ||   ";
				}
			}
		}
		return result;
	}
	
	
	public String entryRoadsWithRedLights() {
		String result = "||  ";
		int cont = 1;
		
		if(entryRoadsList.size() > 0) {
			for(int i = 0; i < entryRoadsList.size(); i++) {
				
				if(!entryRoadsList.get(i).hasGreenLight()) {
					result += entryRoadsList.get(i);
					
					cont++;
					if(cont > 1 )
						result += "  ||   ";
				}
					
			}
		}
		return result;
	}
	
	
	@Override
	protected void completeSectionDetails(IniSection is) {
		String result = "";
		
		if(entryRoadsList.size() > 0) {
			for(int i = 0; i < entryRoadsList.size(); i++) {
				result += entryRoadsList.get(i);
				
				if(i != entryRoadsList.size() - 1)
						result += ",";
			}
		}
		is.setValue("queues",result);
	}
}