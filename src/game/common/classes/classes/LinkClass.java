package game.common.classes.classes;

import game.common.classes.AbstractClass;

public class LinkClass extends AbstractClass {
	
	//Base stats
		private static float health = 140;
		private static float moveSpeed = 80;
		private static float damage = 60;
		private static float resistance = 55;
		private static float energy = 100;
		
		//Regeneration
		private float energyReg = 0.2f;
		private float healthReg = 0.4f;
	
	public LinkClass() {
		super(health, moveSpeed, damage, resistance, energy);
	}
}
