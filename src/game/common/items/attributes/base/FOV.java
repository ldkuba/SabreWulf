package game.common.items.attributes.base;

import game.common.items.attributes.Attribute;

public class FOV extends Attribute{

	private float FOV;
	
	public FOV(float fov) {
		FOV = fov;
	}
	
	public float getValue() {
		return FOV;
	}
	
}
