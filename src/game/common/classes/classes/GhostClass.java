package game.common.classes.classes;

import game.common.classes.AbstractClass;

public class GhostClass extends AbstractClass {
	
	//Base stats
		private static float health = 90;
		private static float moveSpeed = 70;
		private static float damage = 140;
		private static float resistance = 55;
		private static float energy = 160;
		private static float attackRange = 2.0f;
				
		//Regeneration
		private float energyReg = 0.2f;
		private float healthReg = 0.4f;
		
	
	public GhostClass() {
		super(health, moveSpeed, damage, resistance, energy, attackRange);
		resourcePath = "res/actors/ghost/";
	}
}
