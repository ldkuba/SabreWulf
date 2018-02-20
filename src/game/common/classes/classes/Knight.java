package game.common.classes.classes;

import game.classes.melee.MeleeTank;
import game.common.classes.AbstractClasses;

public class Knight extends AbstractClasses {

	//Base stats
	private static float health = 120;
	private static float moveSpeed = 60;
	private static float damage = 80;
	private static float resistance = 78;
	private static float energy = 95;
			
	//Regeneration
	private float energyReg = 0;
	private float healthReg = 0;
	
	public Knight() {
		super(health, moveSpeed, damage, resistance, energy);
	}
}
