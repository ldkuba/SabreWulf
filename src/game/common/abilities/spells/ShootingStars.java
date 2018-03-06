package game.common.abilities.spells;

import game.common.abilities.AbstractAbility;

public class ShootingStars extends AbstractAbility{
	
	private int damage = 5;

	public ShootingStars(String name, float damage, float cooldown) {
		super(name, damage, cooldown);
	}

	@Override
	public float dealDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	//Image displaying the ability
	
}