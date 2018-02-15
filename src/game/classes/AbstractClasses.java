package game.classes;

public class AbstractClasses {

	private int vitality = 0;
	private int energy = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int defence = 0;
	private int attackDmg = 0;
	private int healing = 0;
	private int energyCost = 0;
	private float attackRange = 0;

	private int exIntell;
	private int exStrength;
	private int exDefence;
	private int exEnergy;
	private int exVitality;
	private int exAttackDmg;
	private int exEnergyCost;

	public enum StatType {
		INTELL, STRENGTH, DEFENCE, VITALITY, ATTACKDMG, ENERGYCOST, ENERGY
	}

	public enum ClassType {
		RANGEDPS, RANGEHEALER, MELEEDPS, MELEETANK
	}

	public AbstractClasses() {

	}

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
		this.healing = healing;
	}

	public void setEnergyCost(int energyCost) {
		this.energyCost = energyCost;
	}

	public void setAttackrange(float range) {
		attackRange = range;
	}

	public int getVitality() {
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

	public int getHealing() {
		return healing;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	public float getAttackRange() {
		return attackRange;
	}

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

	public void addEnergyCost(int exEnergyCost) {
		energyCost = energyCost + exEnergyCost;
	}

	public void addEnergy(int exEnergy) {
		energy = energy + exEnergy;
	}

	public void addAttackRange(float range) {
		attackRange = attackRange + range;
	}

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

	public void addExEnergyCost(int exEnergyCost) {
		this.exEnergyCost = exEnergyCost;
	}

	public void addExEnergy(int exEnergy) {
		this.exEnergy = exEnergy;
	}

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
		case ATTACKDMG:
			addAttackDmg(exAttackDmg);
		case ENERGYCOST:
			addEnergyCost(exEnergyCost);
		case ENERGY:
			addEnergy(exEnergy);
		}
	}

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

	public static StatType getAttackDmgType() {
		return StatType.ATTACKDMG;
	}

	public static StatType getEnergyCostType() {
		return StatType.ENERGYCOST;
	}

	public static StatType getEnergyType() {
		return StatType.ENERGY;
	}

}
