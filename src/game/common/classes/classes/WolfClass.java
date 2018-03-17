package game.common.classes.classes;

import game.common.classes.AbstractClass;

public class WolfClass extends AbstractClass {
	
	//Base stats
	private static float health = 90;
	private static float moveSpeed = 76;
	private static float damage = 100;
	private static float resistance = 110;
	private static float energy = 80;
				
	//Regeneration
	private float energyReg = 0.2f;
	private float healthReg = 0.4f;
		
	
	public WolfClass() {
		super(health, moveSpeed, damage, resistance, energy);
	}
}
