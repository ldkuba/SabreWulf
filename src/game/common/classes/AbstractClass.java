package game.common.classes;

public class AbstractClass {

	protected String resourcePath = "";
	
	//Base stats
	protected float health = 0;
	protected float moveSpeed = 0;
	protected float damage = 0;
	protected float resistance = 0;
	protected float energy = 0;
	protected float attackRange = 0;
	
	//Regeneration
	protected float energyReg = 0;
	protected float healthReg = 0;

	public AbstractClass(float health, float mvSpeed, float damage, float resistance, float energy, float attackRange) {
		this.health = health;
		this.moveSpeed = mvSpeed;
		this.damage = damage;
		this.resistance = resistance;
		this.energy = energy;
		this.attackRange = attackRange;
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
	
	public float getAttackRange()
	{
		return attackRange;
	}
	
	public String getResourcePath()
	{
		return this.resourcePath;
	}
}
