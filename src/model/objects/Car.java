package model.objects;

import java.util.List;
import java.util.Random;

import exceptions.SimulationError;
import ini.IniSection;

public class Car extends Vehicle{
	
	protected int lastBreakDownKm; // last kilometer that a car had a breakdown
	protected int resistance; // number of kilometers a car can resist without having a breakdown
	protected int breakdownDuration;
	protected double breakDownProbability;
	protected Random random;


	public Car(String id, int maxSpeed, int resist, double probability, long seed, int faultDuration,
			List<GenericJunction<?>> iti) throws SimulationError {
		
		super(id, maxSpeed, iti);
		resistance = resist;
		breakdownDuration = faultDuration;
		breakDownProbability = probability;
		random = new Random(seed);
	}


	@Override
	public void advance() {
		
		if(getBreakDownDuration() > 0) 
			lastBreakDownKm = kilometrage; // update lastBreakDown
		else {
			int totalKm = kilometrage - lastBreakDownKm; 
			
			// We set a random breakDownDuration
			if(totalKm > resistance && random.nextDouble() <= breakDownProbability) {
				
				setBreakdownDuration(random.nextInt(breakdownDuration) + 1);
			}
		}
		// Advance the car
		super.advance();
	}


	@Override
	protected void completeSectionDetails(IniSection is) {
		super.completeSectionDetails(is);
		is.setValue("type","car");
	}
		
}
