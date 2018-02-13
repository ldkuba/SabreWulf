package game.classes.melee;

import game.classes.AbstractClasses;

public class MeleeClass extends AbstractClasses{
	
	private int health = 100;
	private int intell = 60;
	private int defence = 80;
	private int strength = 130;
	
	public MeleeClass() {
		setHealth(health);
		setIntelligence(intell);
		setDefence(defence);
		setStrength(strength);
	}
	
}
