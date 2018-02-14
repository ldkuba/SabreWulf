package game.classes.range;

import game.classes.AbstractClasses;

public class RangeClass extends AbstractClasses {

	private int vitality = 100;
	private int energy = 80;
	private int intelligence = 90;
	private int defence = 70;
	private int strength = 70;
	private int attackDmg = 10;
	private int healing = 10;
	private int energyCost = 20;

	public RangeClass() {
		setVitality(vitality);
		setEnergy(energy);
		setIntelligence(intelligence);
		setDefence(defence);
		setStrength(strength);
		setAttack(attackDmg);
		setHealing(healing);
		setEnergyCost(energyCost);
	}

}
