package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

/**
 * Damage attribute that affects the player's damage
 */

public class Damage extends Attribute{

	private float damage;

	/**
	 * Set Damage that the attribute will have
	 * @param dmg
	 */
	public Damage(float dmg) {
		damage = dmg;
	}

	/**
	 * Get Damage
	 *
	 * @return
	 */
	@Override
	public float getValue() {
		return damage;
	}
	
}
