/*
 * Original Health: 100
 * MeleeTank Health: 200
 */

import game.common.actors.Player;

public class MeleeTank extends game.classes.melee.MeleeClass {

	private int exVitality = 100;
	private int exEnergy   = 0;
	private int exIntell   = 0;
	private int exStrength = 0;
	private int exDefence  = 0;

	public MeleeTank(StatType type) {
		addExtraStats(exVitality, exEnergy, exIntell, exStrength, exDefence);
		setStatType(type);
	}

	public void attack(Player p1, Player p2) {
	}

}
