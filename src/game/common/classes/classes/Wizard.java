package game.common.classes.classes;

import game.common.classes.AbstractClasses;

public class Wizard extends AbstractClasses {
	
	//Base stats
		private static float health = 90;
		private static float moveSpeed = 70;
		private static float damage = 140;
		private static float resistance = 55;
		private static float energy = 160;
				
		//Regeneration
		private float energyReg = 0;
		private float healthReg = 0;
		
	
	public Wizard() {
		super(health, moveSpeed, damage, resistance, energy);
	}
}
