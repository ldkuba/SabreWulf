package game.classes.melee;

import game.classes.AbstractClasses;

public class MeleeClass extends AbstractClasses {

	private int vitality = 100;
	private int intell = 60;
	private int defence = 80;
	private int strength = 130;

	public MeleeClass() {
		setVitality(vitality);
		setIntelligence(intell);
		setDefence(defence);
		setStrength(strength);
	}

}