package game.common.abilities;

public abstract class AbstractAbility {

	private String name;
	private float cooldown;	//Seconds
	private float damage;
	
	public AbstractAbility(String name, float cooldown, float damage) {
		this.name = name;
		this.cooldown = cooldown;
		this.damage = damage;
	}
	
	public abstract float dealDamage();
	
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
		return damage;
	}
	
	public void setDamage(float dmg) {
		damage = dmg;
	}
}