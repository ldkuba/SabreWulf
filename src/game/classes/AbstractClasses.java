package game.classes;

public abstract class AbstractClasses {

	private int health = 0;
	private int intelligence = 0;
	private int strength = 0;
	private int defence = 0;
	
	private int exIntell;
	private int exStrength;
	private int exDefence;
	private int exHealth;
	
	public enum StatType {INTELL, STRENGTH, DEFENCE, HEALTH}
	
	public AbstractClasses() {
		
	}
	
	public void setHealth(int health) {
		this.health = health;
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
	
	public int getHealth() {
		return health;
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
	
	public void addHealth(int exHealth) {
		health = health + exHealth;
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
	
	public void addExHealth(int exHealth) {
		this.exHealth = exHealth;
	}
	
	public void addExIntelligence(int exIntell) {
		this.exIntell = exIntell;
	}
	
	public void addExStrength(int exStrength ) {
		this.exStrength = exStrength;
	}
	
	public void addExDefence(int exDefence) {
		this.exDefence = exDefence;
	}
	
	public void setStatType(StatType type) {
			switch(type) {
			case INTELL:
				addIntelligence(exIntell);
			case STRENGTH:
				addStrength(exStrength);
			case DEFENCE:
				addDefence(exDefence);
			case HEALTH:
				addHealth(exHealth);
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
	
	public static StatType getHealthType() {
		return StatType.HEALTH;
	}
}
