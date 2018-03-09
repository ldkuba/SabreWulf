package game.common.classes.classes;

import game.common.classes.AbstractClasses;

public class Elf extends AbstractClasses {
	
	//Base stats
		private static float health = 140;
		private static float moveSpeed = 80;
		private static float damage = 60;
		private static float resistance = 55;
		private static float energy = 100;
		
		//Regeneration
		private float energyReg = 0;
		private float healthReg = 0;
	
	public Elf() {
		super(health, moveSpeed, damage, resistance, energy);
	}
}
