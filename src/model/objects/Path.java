package model.objects;

import ini.IniSection;

public class Path extends Road {

	public Path(String id, int length, int maxSpeed, GenericJunction<?> src,
				  GenericJunction<?> dest) {
		super(id, length, maxSpeed, src, dest);
	}
	
	 @Override
	 protected int calculateBaseVelocity() { return maxSpeed; }
	 
	 @Override
	 protected int calculateReductionFactor(int obstacles) {
		 return obstacles + 1;
	 }

	@Override
	protected void completeSectionDetails(IniSection is) {
		super.completeSectionDetails(is);
		is.setValue("type", "dirt");
	}
	 
}
