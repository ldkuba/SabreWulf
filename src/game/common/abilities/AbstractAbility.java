package game.common.abilities;

/**
 * All abilities will
 */

public abstract class AbstractAbility {

	protected String name;
	protected float cooldown;	//Seconds
	protected float extraDamage;
	protected float duration;
	
	public AbstractAbility(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.extraDamage = damage;
	}

	public abstract float dealDamage(float damage);

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getDamage() {
		return extraDamage;
	}
	
	public void setDamage(float dmg) {
		extraDamage = dmg;
	}

	public void setDuration(float dur) {
		duration = dur;
	}

	public float getDuration() {
		return duration;
	}
}