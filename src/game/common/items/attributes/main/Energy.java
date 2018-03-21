package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

/**
 * Energy attribute that affects the player's damage
 */

public class Energy extends Attribute{

	private float energy;

	/**
	 * Set energy the atribute will have
	 * @param ener
	 */
	public Energy(float ener) {
		energy = ener;
	}

	/**
	 * Get energy
	 *
	 * @return
	 */
	@Override
	public float getValue() {
		return energy;
	}
	
}
