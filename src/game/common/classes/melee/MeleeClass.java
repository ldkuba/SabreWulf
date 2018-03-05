package game.common.classes.melee;

import game.common.classes.AbstractClasses;

public class MeleeClass extends AbstractClasses {

	//Base Stats.
	private static float vitality = 100;
	private static int energy   = 60;
	private static int damage   = 60;
	private static int resistance = 70;
	private static int defence  = 80;
	private static float mana = 70;
	private static float moveSpeed = 20f;
	
	private static float fieldOfView = 5.0f;
	
	//Base Combat Stats.
	private static int attackDmg   = 10;
	private static float attackRange = 2.0f;
	private static float attackSpeed = 3.0f;
	
	private static float manaReg = 2.0f;
	private static float healthReg = 2.5f;

	public MeleeClass() {
		super(vitality, moveSpeed, damage, resistance, energy);
	}

}
