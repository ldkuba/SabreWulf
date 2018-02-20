package game.common.player;

import java.util.ArrayList;
import java.util.List;

import game.common.classes.AbstractClasses;
import game.common.items.AbstractItem;

/*
 * Manipulate Players stats.
 */

public class Player {
	
	private int MAX_ITEMS = 6;
	
	//Starting values. Given by the Server.
	private String name;
	private AbstractClasses role;
	private int team;
	
	private int level;
	
	private List<AbstractItem> items = new ArrayList<AbstractItem>(MAX_ITEMS);
	
	//Extra values.
	
	private float VITALITY_LIMIT;
	private float MANA_LIMIT;
	
	private float exVitality = 0;
	
	private int exEnergy = 0;
	private int exStrength = 0;
	private int exDefence = 0;
	private int exIntell = 0;
	
	private float exMana = 0;
	
	private float exMvSpd = 0;
	
	private float exFOV = 0;
	
	private int exAttackDmg = 0;
	private float exAttackRng = 0;
	private float exAttackSpd = 0;
	
	private float exManaReg = 0;
	private float exHealthReg = 0;
	
	
	
	public Player(String name, int team, AbstractClasses role) {
		this.name = name;
		this.team = team;
		this.role = role;
		setStartingAttributes();
	}
	
	private void setStartingAttributes() {
		VITALITY_LIMIT = role.getVitality();
		MANA_LIMIT = role.getMana();
	}
	
	public AbstractClasses getCharClass() {
		return role;
	}
	
	//-------Managing Inventory------
	
	public boolean addItem(AbstractItem item) {
		if(items.size() == MAX_ITEMS) {
			return false;
		}
		items.add(item);
		return true;
	}
	
	public void remvItem(AbstractItem item) {
		items.remove(item);
	}
	
	//--------Managing Stats----------
	public void addMaxVital(float vital) {
		VITALITY_LIMIT = VITALITY_LIMIT + vital;
	}
	
	public void reduceMaxVital(float vital) {
		VITALITY_LIMIT = VITALITY_LIMIT - vital;
	}
	
	public void addMaxMana(float mana) {
		MANA_LIMIT = MANA_LIMIT + mana;
	}
	
	public void redeuceMaxMana(float mana) {
		MANA_LIMIT = MANA_LIMIT - mana;
	}
	
	//--------Manipulate stats-----

	public void addExIntelligence(int intel) {
		exIntell += intel;
		role.incrIntell(exIntell);
	}
	
	public void decrExIntelligence(int intell) {
		exIntell -= intell;
		role.decrIntell(exIntell);
	}

	public void addExStrength(int str) {
		exStrength += str;
		role.incrStrength(exStrength);
	}
	
	public void decrExStrength(int str) {
		exStrength -= str;
		role.decrStrength(exStrength);
	}

	public void addExDefence(int def) {
		exDefence += def;
		role.incrDefence(exDefence);
	}
	
	public void decrExDefence(int def) {
		exDefence -= def;
		role.decrDefence(exDefence);
	}
	
	public void addExEnergy(int enrg) {
		exEnergy += enrg;
		role.incrEnergy(exEnergy);
	}
	
	public void decrExEnergy(int enrg) {
		exEnergy -= enrg;
		role.decrEnergy(exEnergy);
	}
	
	public void addExMoveSpeed(float spd) {
		exMvSpd += spd;
		role.incrMoveSpeed(exMvSpd);
	}
	
	public void decrExMoveSpeed(float spd) {
		exMvSpd -= spd;
		role.decrMoveSpeed(exMvSpd);
	}

	public void addExAttackDmg(int dmg) {
		exAttackDmg += dmg;
		role.incrAttackDmg(exAttackDmg);
	}
	
	public void decrExAttackDmg(int dmg) {
		exAttackDmg -= dmg;
		role.decrAttackDmg(exAttackDmg);
	}
	
	public void addExAttackRng(float rng) {
		exAttackRng += rng;
		role.incrAttackRange(exAttackRng);
	}
	
	public void decrExAttackRng(float rng) {
		exAttackRng -= rng;
		role.decrAttackRange(exAttackRng);
	}
	
	public void addExAttackSpd(float spd) {
		exAttackSpd += spd;
		role.incrAttackSpeed(exAttackSpd);
	}
	
	public void decrExAttackSpeed(float spd) {
		exAttackSpd -= spd;
		role.decrAttackSpeed(exAttackSpd);
	}
	
	public void addExFOV(float view) {
		exFOV += view;
		role.incrFOV(exFOV);
	}
	
	public void decrExFov(float view) {
		exFOV -= view;
		role.decrFOV(exFOV);
	}
	
	public void addExManaReg(float reg) {
		exManaReg += reg;
		role.incrManaReg(exManaReg);
	}
	
	public void decrExManaReg(float reg) {
		exManaReg -= reg;
		role.decrManaReg(exManaReg);
	}
	
	public void addExVitalReg(float reg) {
		exHealthReg += reg;
		role.incrVitalReg(reg);
	}
	
	public void decrExVitalReg(float reg) {
		exHealthReg -= reg;
		role.decrVitalReg(exHealthReg);
	}
	
	//-------Add incrementing stats to the base stats------
	
}