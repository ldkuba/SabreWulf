package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

public class Resistance extends Attribute{
	
	private float resistance;
	
	public Resistance(float res) {
		resistance = res;
	}
	
	@Override
	public float getValue() {
		return resistance;
	}

}
