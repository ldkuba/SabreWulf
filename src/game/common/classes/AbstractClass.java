package game.common.classes;

import game.common.logic.actions.Action;

import java.util.ArrayList;

/**
 * This class is used to set up all the basic methods and stats each actor class
 * has. It is extended by all of the actor types.
 * 
 * @author SabreWulf
 *
 */
public class AbstractClass {

	protected String resourcePath = "";
	protected String name;

	// Base stats
	protected float health = 0;
	protected float moveSpeed = 0;
	protected float damage = 0;
	protected float resistance = 0;
	protected float energy = 0;
	protected float attackRange = 0;

	// Animations
	protected int moveAnimationLength = 0;
	protected int moveAnimationLeft = 0;
	protected int moveAnimationRight = 0;
	protected int moveAnimationUp = 0;
	protected int moveAnimationDown = 0;

	// Regeneration
	protected float energyReg = 0;
	protected float healthReg = 0;

	//Abilities
	protected Action baseAttack;
	protected ArrayList<Action> abilities;

	public AbstractClass() {
	}

	// ----------Get base Stats--------
	/**
	 * Get health
	 * 
	 * @return
	 */
	public float getHealth() {
		return health;
	}

	/**
	 * Get movement speed
	 * 
	 * @return
	 */
	public float getMoveSpeed() {
		return moveSpeed;
	}

	/**
	 * Get energy
	 * 
	 * @return
	 */
	public float getEnergy() {
		return energy;
	}

	/**
	 * Get damage
	 * 
	 * @return
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * Get resistance
	 * 
	 * @return
	 */
	public float getResistance() {
		return resistance;
	}

	/**
	 * Get attack range
	 * 
	 * @return
	 */
	public float getAttackRange() {
		return attackRange;
	}

	/**
	 * Get the animation length for movement
	 * 
	 * @return
	 */
	public int getMoveAnimationLength() {
		return moveAnimationLength;
	}

	/**
	 * Move animation left
	 * 
	 * @return
	 */
	public int getMoveAnimationLeft() {
		return moveAnimationLeft;
	}

	/**
	 * Move animation right
	 * 
	 * @return
	 */
	public int getMoveAnimationRight() {
		return moveAnimationRight;
	}

	/**
	 * Move animation up
	 * 
	 * @return
	 */
	public int getMoveAnimationUp() {
		return moveAnimationUp;
	}

	/**
	 * Move animation down
	 * 
	 * @return
	 */
	public int getMoveAnimationDown() {
		return moveAnimationDown;
	}

	/**
	 * Get the energy regeneration
	 * 
	 * @return
	 */
	public float getEnergyReg() {
		return energyReg;
	}

	/**
	 * Get the health regeneration
	 * 
	 * @return
	 */
	public float getHealthReg() {
		return healthReg;
	}

	/**
	 * Get the resource path
	 * 
	 * @return
	 */
	public String getResourcePath() {
		return this.resourcePath;
	}

	/**
	 * Get the special abilities
	 *
	 * @return
	 */
	public ArrayList<Action> getAbilities() {
		return abilities;
	}

	public Action getBaseAttack() {
		return baseAttack;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
