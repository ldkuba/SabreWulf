package game.classes.range;

/*
 * Original Intelligence = 90
 * RangeDPSIntel Intelligence = 130
 */

public class RangeDPS extends RangeClass {

	private int exIntell = 40;
	private int exStrength = 60;
	private int exDefence = 30;
	private int exVitality = 30;

	// Intelligent based
	public RangeDPS(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExVitality(exVitality);
		setStatType(type);
	}

	public void attack() {

	}

}