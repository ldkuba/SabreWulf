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
	
	private List<AbstractItem> items = new ArrayList<AbstractItem>(MAX_ITEMS);
	
	//Manipulate values.
	
	private float VITALITY_LIMIT;
	private float vitality;
	
	private int energy;
	private int strength;
	private int defence;
	private int intelligence;
	
	private float MANA_LIMIT;
	private float mana;
	
	private float moveSpeed;
	
	private float fieldOfView;
	
	private int attackDmg = 0;
	private float attackRange = 0;
	private float attackSpeed = 0;
	
	private float manaReg;
	private float healthReg;
	
	
	
	public Player(String name, int team, AbstractClasses role) {
		this.name = name;
		this.team = team;
		this.role = role;
		setStartingAttributes();
	}
	
	private void setStartingAttributes() {
		vitality = role.getVitality();
		energy = role.getEnergy();
		strength = role.getStrength();
		defence = role.getDefence();
		intelligence = role.getIntelligence();
		mana = role.getMana();
		moveSpeed = role.getMoveSpeed();
		
		fieldOfView = role.getFOV();
		attackDmg = role.getAttackDmg();
		attackRange = role.getAttackRange();
		attackSpeed = role.getAttackSpeed();
		manaReg = role.getManaReg();
		healthReg = role.getHealthReg();
		
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
	
	public void lostVitality(int damage) {
		vitality = vitality - damage;
	}
	
	public void gainVitality(float vital) {
		vitality = vitality + vital;
	}
	
	public void gainEnergy(int gainergy) {
		energy = energy + gainergy;
	}
	
	public void lostEnergy(int losergy) {
		energy = energy - losergy;
	}
	
	public void gainIntelligence(int intell) {
		intelligence = intelligence + intell;
	}
	
	public void lostIntelligence(int intell) {
		intelligence = intelligence - intell;
	}
	
	public void gainStrength(int strength) {
		this.strength = this.strength + strength;
	}
	
	public void lostStrength(int strength) {
		this.strength = this.strength - strength;
	}
	
	public void gainDefence(int defence) {
		this.defence = this.defence + defence;
	}
	
	public void lostDefence(int defence) {
		this.defence = this.defence - defence;
	}
	
	public void gainMana(float mana) {
		this.mana = this.mana + mana;
	}
	
	public void lostMana(float mana) {
		this.mana = this.mana - mana;
	}
	
	public void gainSpeed(float moveSpeed) {
		this.moveSpeed = this.moveSpeed + moveSpeed;
	}
	
	public void lostSpeed(float moveSpeed) {
		this.moveSpeed = this.moveSpeed - moveSpeed;
	}
	
	public void gainVision(float vision) {
		fieldOfView = fieldOfView + vision;
	}
	
	public void lostVision(float vision) {
		fieldOfView = fieldOfView - vision;
	}
	
	public void incrAttackDmg(int dmg) {
		attackDmg = attackDmg + dmg;
	}
	
	public void decrAttackDmg(int dmg) {
		attackDmg = attackDmg - dmg;
	}
	
	public void incrAttackRng(float rng) {
		attackRange = attackRange + rng;
	}
	
	public void decrAttackRng(float rng) {
		attackRange = attackRange - rng;
	}
	
	public void incrAttackSpd(float spd) {
		attackSpeed = attackSpeed + spd;
	}
	
	public void decrAttackSpd(float spd) {
		attackSpeed = attackSpeed - spd;
	}
	
	public void incrManaReg(float reg) {
		manaReg = manaReg + reg;
	}
	
	public void decrManaReg(float reg) {
		manaReg = manaReg - reg;
	}
	
	public void incrVitalReg(float reg) {
		healthReg = healthReg + reg;
	}
	
	public void decrVitalReg(float reg) {
		healthReg = healthReg - reg;
	}
	
}