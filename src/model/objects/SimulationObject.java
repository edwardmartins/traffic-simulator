package model.objects;

import exceptions.SimulationError;
import ini.IniSection;

public abstract class SimulationObject {

	protected String id;

	public SimulationObject(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String generateInform(int time) {
		IniSection is = new IniSection(this.getSectionName()); 
		is.setValue("id", id); 
		is.setValue("time", time); 
		this.completeSectionDetails(is); 
		return is.toString();
	}

	// Move the simulation Object, implemented in subclasses
	public abstract void advance() throws SimulationError;
	
	protected abstract String getSectionName(); 
	
	protected abstract void completeSectionDetails(IniSection is);
	
	@Override
	public String toString() {
		return id;
	}
}
