package game.common.abilities;

/**
 * This class is used to set up all the basic properties of all abilities used by the characters in the game
 * @author SabreWulf
 *
 */

public abstract class AbstractAbility {

	protected String name;
	protected float cooldown;	//Seconds
	protected float extraDamage;
	protected float duration;
	
	/**
	 * Creates an abstract ability and set its properties
	 * @param name
	 * @param cooldown
	 * @param damage
	 */
	public AbstractAbility(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.extraDamage = damage;
	}

	/**
	 * Abstract method to deal damage with the abstract ability
	 * @param damage
	 * @return
	 */
	public abstract float dealDamage(float damage);

	/**
	 * Sets the cooldown of the abstract ability
	 * @param cooldown
	 */
	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
	
	/**
	 * Gets the cooldown of the abstract ability
	 * @return
	 */
	public float getCooldown() {
		return cooldown;
	}
	
	/**
	 * Gets the name of the abstract ability
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the abstract ability
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the damage of the abstract ability
	 * @return
	 */
	public float getDamage() {
		return extraDamage;
	}
	
	/**
	 * Sets the damage of the abstract ability
	 * @param dmg
	 */
	public void setDamage(float dmg) {
		extraDamage = dmg;
	}

	/**
	 * Sets the duration of the abstract ability
	 * @param dur
	 */
	public void setDuration(float dur) {
		duration = dur;
	}

	/**
	 * Gets the duration of the abstract ability
	 * @return
	 */
	public float getDuration() {
		return duration;
	}
}