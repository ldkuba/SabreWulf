package game.classes.melee;

import game.player.Player;

/*
 * Original Health: 100
 * MeleeTank Health: 200
 */

public class MeleeTank extends MeleeClass {

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
		int damageDealt = p1.getCharClass().getAttackDmg() + (p1.getCharClass().getStrength() / 10);
		int damageReceived = damageDealt - (p2.getCharClass().getDefence() / 10);
		int p2Vitality = p2.getVitality();
		p2.setVitality(p2Vitality - damageReceived);
		int p1Energy = p1.getEnergy();
		p1.setEnergy(p1Energy - p1.getCharClass().getEnergyCost());
	}

}
