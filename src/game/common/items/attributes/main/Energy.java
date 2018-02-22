package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

public class Energy extends Attribute{

	private float energy;
	
	public Energy(int ener) {
		energy = ener;
	}

	@Override
	public float getValue() {
		return energy;
	}
	
}
