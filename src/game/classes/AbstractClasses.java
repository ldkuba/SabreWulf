package game.classes;

public class AbstractClasses {

	private int vitality = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int defence = 0;

	private int exIntell;
	private int exStrength;
	private int exDefence;
	private int exVitality;

	public enum StatType {
		INTELL, STRENGTH, DEFENCE, VITALITY
	}

	public AbstractClasses() {

	}

	public void attack() {

	}

	public void takeDamage() {

	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
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

	public int getVitality() {
		return vitality;
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
}
