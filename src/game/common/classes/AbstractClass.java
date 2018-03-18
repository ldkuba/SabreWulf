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
	
	//Animations
	protected int moveAnimationLength = 0;
	protected int moveAnimationLeft = 0;
	protected int moveAnimationRight = 0;
	protected int moveAnimationUp = 0;
	protected int moveAnimationDown = 0;
	
	//Regeneration
	protected float energyReg = 0;
	protected float healthReg = 0;

	public AbstractClass() {
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
	
	public int getMoveAnimationLength()
	{
		return moveAnimationLength;
	}

	public int getMoveAnimationLeft()
	{
		return moveAnimationLeft;
	}

	public int getMoveAnimationRight()
	{
		return moveAnimationRight;
	}

	public int getMoveAnimationUp()
	{
		return moveAnimationUp;
	}

	public int getMoveAnimationDown()
	{
		return moveAnimationDown;
	}

	public float getEnergyReg()
	{
		return energyReg;
	}

	public float getHealthReg()
	{
		return healthReg;
	}

	public String getResourcePath()
	{
		return this.resourcePath;
	}
}
