package game.common.classes.classes;

import game.common.classes.AbstractClasses;

public class Orc extends AbstractClasses {
	
	//Base stats
	private static float health = 90;
	private static float moveSpeed = 76;
	private static float damage = 100;
	private static float resistance = 110;
	private static float energy = 80;
				
	//Regeneration
	private float energyReg = 0;
	private float healthReg = 0;
		
	
	public Orc() {
		super(health, moveSpeed, damage, resistance, energy);
	}
}
