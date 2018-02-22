package game.classes.range;

import game.classes.AbstractClasses;

public class RangeClass extends AbstractClasses {

	//Base Stats.
	private static int vitality = 100;
	private static int energy = 80;
	private static int intelligence = 90;
	private static int strength = 70;
	private static int defence = 70;
	
	//Base Combat Stats
	private int attackDmg = 10;
	private float baseRange = 6.0f;

	public RangeClass() {
		super(vitality, energy, intelligence, strength, defence);
		setCombatStats(attackDmg, baseRange);
	}

}
