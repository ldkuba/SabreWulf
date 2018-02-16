package game.classes.melee;

import game.classes.AbstractClasses;

public class MeleeClass extends AbstractClasses {

	//Base Stats.
	private static int vitality = 100;
	private static int energy   = 40;
	private static int intell   = 60;
	private static int strength = 130;
	private static int defence  = 80;
	
	//Base Combat Stats.
	private static int attackDmg   = 10;
	private static float baseRange = 2.0f;

	public MeleeClass() {
		super(vitality, energy, intell, strength, defence);
		setCombatStats(attackDmg, baseRange);
	}

}
