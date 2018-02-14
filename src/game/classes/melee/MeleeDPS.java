package game.classes.melee;

import game.player.Player;

/*
 * Original Strength: 130
 * MeleeDPS Strength = 190
 */
public class MeleeDPS extends MeleeClass {

	private int exStrength = 60;
	private int exIntell;
	private int exDefence;
	private int exVitality;
	private int exEnergyCost;
	private int exEnergy;

	public MeleeDPS(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExVitality(exVitality);
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

}
