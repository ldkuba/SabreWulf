package game.common.classes;

public class AbstractClass {

	//Base stats
	private float health = 0;
	private float moveSpeed = 0;
	private float damage = 0;
	private float resistance = 0;
	private float energy = 0;
	
	//Regeneration
	private float energyReg = 0;
	private float healthReg = 0;

	public AbstractClass(float health, float mvSpeed, float damage, float resistance, float energy) {
		this.health = health;
		this.moveSpeed = mvSpeed;
		this.damage = damage;
		this.resistance = resistance;
		this.energy = energy;
	}
	
	//-------Set Base Stats-------
	public void setEnergyRegen(float energyReg) {
		this.energyReg = energyReg;
	}
	
	public void setHealthRegen(float healthReg) {
		this.healthReg = healthReg;
	}

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
	
	public void changeDamage(float dmg) {
		damage += dmg;
	}
	
	public void changeMoveSpeed(float spd) {
		moveSpeed += spd;
	}
	
	public void changeResistance(float res) {
		resistance += res;
	}
	
	public void changeHealth(float heal) {
		health += heal;
	}
	
	public void changeEnergy(float ener) {
		energy += ener;
	}
	
	public void changeEnergyReg(float reg) {
		energyReg += reg;
	}
	
	public void changeHealthReg(float reg) {
		healthReg += reg;
	}

}
