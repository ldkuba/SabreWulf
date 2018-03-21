package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

/**
 * Resistance attribute that affects the player's damage
 */

public class Resistance extends Attribute{
	
	private float resistance;

	/**
	 * Set resistance that the attribute will have
	 * @param res
	 */
	public Resistance(float res) {
		resistance = res;
	}

	/**
	 * Get resistance
	 * @return
	 */
	@Override
	public float getValue() {
		return resistance;
	}

}
