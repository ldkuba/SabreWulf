package game.common.items.attributes.main;

import game.common.items.attributes.Attribute;

public class MovementSpeed extends Attribute{

	private float moveSpeed;
	
	public MovementSpeed(float spd) {
		moveSpeed = spd;
	}
	
	@Override
	public float getValue() {
		return moveSpeed;
	}
	
}
