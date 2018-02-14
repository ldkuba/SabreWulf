package game.classes.range;

import game.player.Player;

/*
 * Original Intelligence: 90
 * RangeHealer Intelligence: 170
 */

public class RangeHealer extends RangeClass {

	private int exIntell = 80;
	private int exStrength;
	private int exDefence;
	private int exVitality;
	private int exEnergyCost;
	private int exEnergy;

	public RangeHealer(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExVitality(exVitality);
		addExEnergy(exEnergy);
		addExEnergyCost(exEnergyCost);
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

	public void heal(Player p1, Player p2) {
		int p1Healing = p1.getCharClass().getHealing() + (p1.getCharClass().getIntelligence() / 10);
		int p2Vitality = p2.getVitality();
		p2.setVitality(p2Vitality + p1Healing);
		int p1Energy = p1.getEnergy();
		p1.setEnergy(p1Energy - p1.getCharClass().getEnergyCost());
	}

}
