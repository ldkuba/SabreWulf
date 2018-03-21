package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

/**
 * Movement speed attribute that affects the player's damage
 */

public class MovementSpeed extends Attribute{

	private float moveSpeed;

	/**
	 * Set movement speed the attribute will have
	 * @param spd
	 */
	public MovementSpeed(float spd) {
		moveSpeed = spd;
	}

	/**
	 * Get movement speed
	 * @return
	 */
	@Override
	public float getValue() {
		return moveSpeed;
	}
	
}
