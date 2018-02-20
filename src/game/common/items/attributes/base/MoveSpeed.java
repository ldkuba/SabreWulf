package game.common.items.attributes.base;

import game.common.items.attributes.Attribute;

public class MoveSpeed extends Attribute{

	private float moveSpeed;
	
	public MoveSpeed(float spd) {
		moveSpeed = spd;
	}
	
	public float getValue() {
		return moveSpeed;
	}
}
