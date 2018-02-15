package game.classes.melee;

/*
 * Original Health: 100
 * MeleeTank Health: 200
 */

public class MeleeTank extends MeleeClass{

	private int exHealth = 100;
	private int exIntell;
	private int exStrength;
	private int exDefence;
	
	public MeleeTank(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExHealth(exHealth);
		setStatType(type);
	}
	
	
}
