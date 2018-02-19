package game.common.classes.range;

import game.common.player.Player;

/*
 * Original Intelligence: 90
 * RangeHealer Intelligence: 170
 */

public class RangeHealer extends RangeClass {

	private int exIntell = 80;
	private int exStrength = 0;
	private int exDefence = 0;
	private int exVitality = 0;
	private int exEnergy = 0;

	private int exAttackDmg = 0;
	private int exAttackRng = 0;

	public RangeHealer(StatType type) {
		addExtraStats(exVitality, exEnergy, exIntell, exStrength, exDefence);
		addExtraCombatStats(exAttackDmg, exAttackRng);
		setStatType(type);
	}

}
