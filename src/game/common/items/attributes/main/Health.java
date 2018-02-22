package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

public class Health extends Attribute{

	private float health;
	
	public Health(float health) {
		this.health = health;
	}
	
	public float getValue() {
		return health;
	}
	
}
