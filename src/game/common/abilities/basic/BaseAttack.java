package game.common.abilities.basic;

import game.common.abilities.AbstractAbility;

public class BaseAttack extends AbstractAbility{

	//Image displaying the ability
	//Seconds
	
	public BaseAttack(String name, float cooldown, float damage) {
		super(name, cooldown, damage);
	}
	
	@Override
	public float dealDamage() {
		return getDamage();
	}	
}
