package game.common.player;

import game.common.classes.AbstractClasses;

/*
 * Manipulate Players stats.
 */

public class Player {
	
	//Starting values. Given by the Server.
	private AbstractClasses role;
	private int team;
	
	//Manipulate values.
	private int vitality;
	private int energy;
	private int strength;
	private int defence;
	private int intelligence;
	
	public Player(AbstractClasses role) {
		setStartingAttributes();
	}
	
	private void setStartingAttributes() {
		vitality = role.getVitality();
		energy = role.getEnergy();
		strength = role.getStrength();
		defence = role.getDefence();
		intelligence = role.getIntelligence();
	}
	
	public AbstractClasses getCharClass() {
		return role;
	}
	
}