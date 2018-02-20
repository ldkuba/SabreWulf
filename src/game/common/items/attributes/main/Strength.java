package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

public class Strength extends Attribute{

	private int strength;
	
	public Strength(int strength) {
		this.strength = strength;
	}
	
	public int getValue() {
		return strength;
	}
	
}
