package game.common.classes.range;

import game.common.classes.AbstractClasses;

public class RangeClass extends AbstractClasses {

	//Base Stats.
	private static float vitality = 100;
	private static int energy = 80;
	private static int intelligence = 90;
	private static int strength = 70;
	private static int defence = 70;
	private static float mana = 90;
	private static float moveSpeed = 40;
	
	private static float fieldOfView = 5.5f;
	
	//Base Combat Stats
	private static int attackDmg = 10;
	private static float attackRange = 6.0f;
	private static float attackSpeed = 4.0f;
	
	//Base reg stats
	private static float manaReg = 4.7f;
	private static float healthReg = 3.5f;

	public RangeClass() {
		super(vitality, energy, intelligence, strength, defence, fieldOfView,mana);
		setCombatStats(attackDmg, attackRange, attackSpeed);
		setRegStats(manaReg, healthReg);
	}

}
