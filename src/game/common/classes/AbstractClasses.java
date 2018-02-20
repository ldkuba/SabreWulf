package game.common.classes;

public class AbstractClasses {

	//Base stats
	private float vitality = 0;
	private int energy = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int defence = 0;
	private float mana = 0;
	private float moveSpeed = 0;
	
	private float fieldOfView = 0;
	
	//Attacks
	private int attackDmg = 0;	//Basic attack damage
	private float attackRange = 0;
	private float attackSpeed = 0;
	
	//Regeneration
	private float manaReg = 0;
	private float healthReg = 0;
	
	private int exIntell;
	private int exStrength;
	private int exDefence;
	private int exEnergy;
	private int exVitality;
	private float exMana;
	private float exFieldOfView;
	
	private int exAttackDmg;
	private float exAttackRng;
	private float exAttackSpeed;

	
	public enum StatType {
		INTELL, STRENGTH, DEFENCE, VITALITY, ENERGY
	}

	public enum ClassType {
		RANGEDPS, RANGEHEALER, MELEEDPS, MELEETANK
	}

	public AbstractClasses(float vitality, int energy, int intell, int strength, int defence, float fieldOfView, float mana) {
		this.vitality = vitality;
		this.energy = energy;
		intelligence = intell;
		this.strength = strength;
		this.defence = defence;
		this.fieldOfView = fieldOfView;
		this.mana = mana;
	}
	
	public void setCombatStats(int attackDmg, float attackRange, float attackSpeed) {
		this.attackDmg = attackDmg;
		this.attackRange = attackRange;
		this.attackSpeed = attackSpeed;
	}
	
	public void setRegStats(float manaReg, float healthReg) {
		this.manaReg = manaReg;
		this.healthReg = healthReg;
	}
	
	public void incrStats(int exVitality, int exEnergy, int exIntell, int exStrength, int exDefence, float exFieldOfView, float mana) {
		vitality = vitality + exVitality;
		energy = energy + exEnergy;
		intelligence = intelligence + exIntell;
		strength = strength + exStrength;
		defence = defence + exDefence;
		fieldOfView = fieldOfView + exFieldOfView;
		this.mana = this.mana + mana;
	}
	
	public void incrCombatStats(int exAttackDmg, float exAttackRng, float exAttackSpd) {
		attackDmg = attackDmg + exAttackDmg;
		attackRange = attackRange + exAttackRng;
		attackSpeed = attackSpeed + exAttackSpd;
	}

	//-------Set Base Stats-------
	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setIntelligence(int intel) {
		intelligence = intel;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public void setAttack(int attackDmg) {
		this.attackDmg = attackDmg;
	}

	public void setHealing(int healing) {
		this.healthReg = healing;
	}

	public void setAttackrange(float range) {
		attackRange = range;
	}

	//----------Get base Stats--------
	public float getVitality() {
		return vitality;
	}

	public int getEnergy() {
		return energy;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getStrength() {
		return strength;
	}

	public int getDefence() {
		return defence;
	}

	public int getAttackDmg() {
		return attackDmg;
	}

	public float getHealing() {
		return healthReg;
	}

	public float getAttackRange() {
		return attackRange;
	}
	
	public float getMana() {
		return mana;
	}

	//----------manipulate base stats using---------
	public void incrVitality(float exVitality) {
		vitality = vitality + exVitality;
	}

	public void decrVitality(int dmgTaken) {
		vitality = vitality - dmgTaken;
	}
	
	public void incrEnergy(int exEnergy) {
		energy = energy + exEnergy;
	}
	
	public void decrEnergy(int energy) {
		this.energy = this.energy - energy;
	}

	public void incrIntell(int exIntell) {
		intelligence = intelligence + exIntell;
	}
	
	public void decrIntell(int intell) {
		intelligence = intelligence - intell;
	}

	public void incrStrength(int exStrength) {
		strength = strength + exStrength;
	}
	
	public void decrStrength(int lost) {
		strength = strength - lost;
	}

	public void incrDefence(int exDefence) {
		defence = defence + exDefence;
	}

	public void decrDefence(int def) {
		defence = defence - def;
	}
	
	public void incrAttackDmg(int exAttackDmg) {
		attackDmg = attackDmg + exAttackDmg;
	}
	
	public void decrAttackDmg(int dmg) {
		attackDmg = attackDmg - dmg;
	}
	
	public void incrAttackSpeed(float speed) {
		attackSpeed = attackSpeed + speed;
	}
	
	public void decrAttackSpeed(float speed) {
		attackSpeed = attackSpeed - speed;
	}
	
	public void incrAttackRange(float range) {
		attackRange = attackRange + range;
	}
	
	public void decrAttackRange(float range) {
		attackRange = attackRange - range;
	}
	
	public void incrMana(float mana) {
		this.mana = this.mana + mana;
	}
	
	public void decrMana(float mana) {
		this.mana = this.mana - mana;
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
	
	public void incrMoveSpeed(float spd) {
		moveSpeed = moveSpeed + spd;
	}
	
	public void decrMoveSpeed(float spd) {
		moveSpeed = moveSpeed - spd;
	}
	
	public void incrFOV(float view) {
		fieldOfView = fieldOfView + view;
	}
	
	public void decrFOV(float view) {
		fieldOfView = fieldOfView - view;
	}

	//-----------Set Stat type-----------
	public void setStatType(StatType type) {
		switch (type) {
		case INTELL:
			incrIntell(exIntell);
		case STRENGTH:
			incrStrength(exStrength);
		case DEFENCE:
			incrDefence(exDefence);
		case VITALITY:
			incrVitality(exVitality);
		case ENERGY:
			incrEnergy(exEnergy);
		}
	}

	//---------Get stat type----------
	public static StatType getIntellType() {
		return StatType.INTELL;
	}

	public static StatType getStrengthType() {
		return StatType.STRENGTH;
	}

	public static StatType getDefenceType() {
		return StatType.DEFENCE;
	}

	public static StatType getVitalityType() {
		return StatType.VITALITY;
	}

	public static StatType getEnergyType() {
		return StatType.ENERGY;
	}

}
