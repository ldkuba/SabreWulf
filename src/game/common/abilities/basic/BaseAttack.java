package game.common.abilities.basic;

import game.common.abilities.AbstractAbility;

public class BaseAttack extends AbstractAbility{
	
	public BaseAttack(String name, float cooldown, float extraDamage) {
		super(name, cooldown, extraDamage);
	}

	@Override
	public float dealDamage(float damage) {
		return damage + extraDamage;
	}

}
