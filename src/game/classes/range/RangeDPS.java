package game.classes.range;

import game.player.Player;

/*
 * Original Intelligence = 90
 * RangeDPSIntel Intelligence = 130
 */

public class RangeDPS extends RangeClass {

	private int exIntell = 40;
	private int exStrength = 60;
	private int exDefence = 30;
	private int exVitality = 30;
	private int exAttackDmg = 10;
	private int exEnergyCost;
	private int exEnergy;

	// Intelligent based
	public RangeDPS(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExVitality(exVitality);
		addExAttackDmg(exAttackDmg);
		addExEnergyCost(exEnergyCost);
		addExEnergy(exEnergy);
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

	public void castSpell(Player p1, Player p2) {
		int damageDealt = p1.getCharClass().getAttackDmg() + (p1.getCharClass().getIntelligence() / 2);
		int damageReceived = damageDealt - (p2.getCharClass().getDefence() / 10);
		int p2Vitality = p2.getVitality();
		p2.setVitality(p2Vitality - damageReceived);
		int p1Energy = p1.getEnergy();
		p1.setEnergy(p1Energy - p1.getCharClass().getEnergyCost());
	}
}
