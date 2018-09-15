package model.objects;

import ini.IniSection;

public class Highway extends Road {

	private int lanes;

	public Highway(String id, int length, int maxSpeed, GenericJunction<?> orig,
			GenericJunction<?> dest, int lns) {
		
		super(id, length, maxSpeed, orig, dest);
		lanes = lns;
	}

	@Override
	protected int calculateBaseVelocity() {
		int result = Math.min(maxSpeed, (maxSpeed * lanes) / 
													 Math.max(vehicles.size(), 1) + 1);
		return result;
	}

	@Override
	protected void completeSectionDetails(IniSection is) {
		is.setValue("type", "lanes");
		super.completeSectionDetails(is);
	}

	@Override
	protected int calculateReductionFactor(int obstacles) {
		return obstacles < lanes ? 1 : 2;
	}
	
}
