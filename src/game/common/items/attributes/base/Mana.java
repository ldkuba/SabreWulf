package game.common.items.attributes.base;

import game.common.items.attributes.Attribute;

public class Mana extends Attribute{

	private float mana;
	
	public Mana(float mana) {
		this.mana = mana;
	}
	
	public float getValue() {
		return mana;
	}
}
