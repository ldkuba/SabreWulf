package game.common.classes.range;

/*
 * Original Intelligence = 90
 * RangeDPSIntel Intelligence = 130
 */

public class RangeDPS extends RangeClass {

	private int exVitality  = 30;
	private int exEnergy    = 0;
	private int exIntell    = 40;
	private int exStrength  = 60;
	private int exDefence   = 30;
	
	private int exAttackDmg = 10;
	private float exAttackRng = 0;
	private float exAtckSpd = 0;

	// Intelligent based
	public RangeDPS(StatType type) {
		incrStats(exVitality, exEnergy, exIntell, exStrength, exDefence);
		incrCombatStats(exAttackDmg, exAttackRng, exAtckSpd);
		setStatType(type);
	}
}
