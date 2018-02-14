package game.classes.melee;

/*
 * Original Strength: 130
 * MeleeDPS Strength = 190
 */
public class MeleeDPS extends MeleeClass {

	private int exStrength = 60;
	private int exIntell;
	private int exDefence;
	private int exVitality;

	public MeleeDPS(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExVitality(exVitality);
		setStatType(type);
	}

}
