package game.common.classes;

public class AbstractClasses {

	//Base stats
	private float health = 0;
	private float moveSpeed = 0;
	private float damage = 0;
	private float resistance = 0;
	private float energy = 0;
	
	//Regeneration
	private float energyReg = 0;
	private float healthReg = 0;

	public AbstractClasses(float health, float mvSpeed, float damage, float resistance, float energy) {
		this.health = health;
		this.moveSpeed = mvSpeed;
		this.damage = damage;
		this.resistance = resistance;
		this.energy = energy;
	}
	
	public void setEnergyRegen(float energyReg) {
		this.energyReg = energyReg;
	}
	
	public void setHealthRegen(float healthReg) {
		this.healthReg = healthReg;
	}
	/*
	public void incrStats(int exHealth, float exMvSpd, int dmg, float res, float exEnergy) {
		health = health + exHealth;
		moveSpeed = moveSpeed + exMvSpd;
		damage = damage + dmg;
		resistance = resistance + res;
		energy = energy + exEnergy;
	}
	*/

	//-------Set Base Stats-------
	public void setHealth(int vitality) {
		health = vitality;
	}
	
	public void setMoveSpeed(float spd) {
		moveSpeed = spd;
	}
	
	public void setDamage(int dmg) {
		damage = dmg;
	}
	
	public void setResistance(float res) {
		resistance = res;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	//----------Get base Stats--------
	public float getHealth() {
		return health;
	}
	
	public float getMoveSpeed(){
		return moveSpeed;
	}

	public float getEnergy() {
		return energy;
	}

	public float getDamage() {
		return damage;
	}
	
	public float getResistance() {
		return resistance;
	}
	
	//----------manipulate base stats using---------
	
	public void incrDamage(float dmg) {
		damage += dmg;
	}
	
	public void decrDamage(float dmg) {
		damage -= dmg;
	}
	
	public void incrMoveSpeed(float spd) {
		moveSpeed += moveSpeed + spd;
	}
	
	public void decrMoveSpeed(float spd) {
		moveSpeed -= moveSpeed - spd;
	}
	
	public void incrResistance(float res) {
		resistance += res;
	}
	
	public void decrResistance(float res) {
		resistance -= res;
	}
	
	public void incrHealth(float heal) {
		health += heal;
	}
	
	public void decrHealth(float heal) {
		health -= heal;
	}
	
	public void incrEnergy(float ener) {
		energy += ener;
	}
	
	public void decrEnergy(float ener){
		energy -= ener;
	}
	
	public void incrEnergyReg(float reg) {
		energyReg += reg;
	}
	
	public void decrEnergyReg(float reg) {
		energyReg -= reg;
	}
	
	public void incrHealthReg(float reg) {
		healthReg += healthReg + reg;
	}
	
	public void decrHealthReg(float reg) {
		healthReg -= healthReg - reg;
	}
	

}
