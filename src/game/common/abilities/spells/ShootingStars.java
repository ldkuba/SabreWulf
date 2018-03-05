package game.common.abilities.spells;

import game.common.abilities.AbstractAbility;

public class ShootingStars extends AbstractAbility{

	public ShootingStars(String name, float cooldown, float damage) {
		super(name, cooldown, damage);
	}

	private int damage = 5;

	@Override
	public float dealDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	//Image displaying the ability
	
}