package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

public class Damage extends Attribute{

	private float damage;
	
	public Damage(float dmg) {
		damage = dmg;
	}
	
	@Override
	public float getValue() {
		return damage;
	}
	
}
