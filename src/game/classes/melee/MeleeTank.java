package game.classes.melee;

import game.player.Player;

/*
 * Original Health: 100
 * MeleeTank Health: 200
 */

public class MeleeTank extends MeleeClass {

	private int exVitality = 100;
	private int exIntell;
	private int exStrength;
	private int exDefence;

	public MeleeTank(StatType type) {
		addExIntelligence(exIntell);
		addExStrength(exStrength);
		addExDefence(exDefence);
		addExVitality(exVitality);
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
