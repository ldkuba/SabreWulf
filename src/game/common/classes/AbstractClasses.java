package game.common.classes;

public class AbstractClasses {

	//Base stats
	private float vitality = 0;
	private int energy = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int defence = 0;
	
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
	
	private int exAttackDmg;
	private int exAttackRng;

	
	public enum StatType {
		INTELL, STRENGTH, DEFENCE, VITALITY, ENERGY
	}

	public enum ClassType {
		RANGEDPS, RANGEHEALER, MELEEDPS, MELEETANK
	}

	public AbstractClasses(float vitality, int energy, int intell, int strength, int defence, float fieldOfView) {
		this.vitality = vitality;
		this.energy = energy;
		intelligence = intell;
		this.strength = strength;
		this.defence = defence;
		this.fieldOfView = fieldOfView;
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
	
	public void addExtraStats(int exVitality, int exEnergy, int exIntell, int exStrength, int exDefence) {
		this.exVitality = exVitality;
		this.exEnergy = exEnergy;
		this.exIntell = exIntell;
		this.exStrength = exStrength;
		this.exDefence = exDefence;
	}
	
	public void addExtraCombatStats(int exAttackDmg, int exAttackRng) {
		this.exAttackDmg = exAttackDmg;
		this.exAttackRng = exAttackRng;
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

	//----------increase base stats using---------
	public void addVitality(int exVitality) {
		vitality = vitality + exVitality;
	}

	public void reduceVitality(int dmgTaken) {
		vitality = vitality - dmgTaken;
	}

	public void addIntelligence(int exIntell) {
		intelligence = intelligence + exIntell;
	}

	public void addStrength(int exStrength) {
		strength = strength + exStrength;
	}

	public void addDefence(int exDefence) {
		defence = defence + exDefence;
	}

	public void addAttackDmg(int exAttackDmg) {
		attackDmg = attackDmg + exAttackDmg;
	}

	public void addEnergy(int exEnergy) {
		energy = energy + exEnergy;
	}

	public void addAttackRange(float range) {
		attackRange = attackRange + range;
	}

	//----------Increase extra stats----------
	public void addExVitality(int exVitality) {
		this.exVitality = exVitality;
	}

	public void addExIntelligence(int exIntell) {
		this.exIntell = exIntell;
	}

	public void addExStrength(int exStrength) {
		this.exStrength = exStrength;
	}

	public void addExDefence(int exDefence) {
		this.exDefence = exDefence;
	}

	public void addExAttackDmg(int exAttackDmg) {
		this.exAttackDmg = exAttackDmg;
	}

	public void addExEnergy(int exEnergy) {
		this.exEnergy = exEnergy;
	}

	//-----------Set Stat type-----------
	public void setStatType(StatType type) {
		switch (type) {
		case INTELL:
			addIntelligence(exIntell);
		case STRENGTH:
			addStrength(exStrength);
		case DEFENCE:
			addDefence(exDefence);
		case VITALITY:
			addVitality(exVitality);
		case ENERGY:
			addEnergy(exEnergy);
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
