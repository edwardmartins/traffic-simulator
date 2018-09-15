package model.objects;

import java.util.List;

import exceptions.SimulationError;
import ini.IniSection;

public class Bike extends Vehicle{

	public Bike(String id, int maxSpeed, List<GenericJunction<?>> iti) throws SimulationError {
		super(id, maxSpeed, iti);
	}

	@Override
	public void setBreakdownDuration(Integer breakDownDuration) {
		if(this.currentSpeed >= this.maxVelocity / 2)
			super.setBreakdownDuration(breakDownDuration);
	}

	@Override
	protected void completeSectionDetails(IniSection is) {
		is.setValue("type","bike");
		super.completeSectionDetails(is);
	}
}
