package game.classes.melee;

import game.classes.AbstractClasses;

public class MeleeClass extends AbstractClasses {

	private int vitality = 100;
	private int energy = 40;
	private int intell = 60;
	private int defence = 80;
	private int strength = 130;
	private int attackDmg = 10;
	private int energyCost = 10;
	private float baseRange = 2.0f;

	public MeleeClass() {
		setVitality(vitality);
		setEnergy(energy);
		setIntelligence(intell);
		setDefence(defence);
		setStrength(strength);
		setAttack(attackDmg);
		setEnergyCost(energyCost);
		setBaseRange(baseRange);
	}

}
