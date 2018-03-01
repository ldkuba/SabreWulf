package game.classes.melee;

import game.player.Player;

/*
 * Original Strength: 130
 * MeleeDPS Strength = 190
 */
public class MeleeDPS extends MeleeClass {

	private int exVitality = 0;
	private int exEnergy   = 0;
	private int exIntell   = 0;
	private int exStrength = 60;
	private int exDefence  = 0;

	public MeleeDPS(StatType type) {
		addStrength(exStrength);
		setStatType(type);
	}

	public void attack(NetPlayer p1, NetPlayer p2) {
		int damageDealt = p1.getCharClass().getAttackDmg() + (p1.getCharClass().getStrength() / 10);
		int damageReceived = damageDealt - (p2.getCharClass().getDefence() / 10);
		int p2Vitality = p2.getVitality();
		p2.setVitality(p2Vitality - damageReceived);
		int p1Energy = p1.getEnergy();
		p1.setEnergy(p1Energy - p1.getCharClass().getEnergyCost());
	}

}
