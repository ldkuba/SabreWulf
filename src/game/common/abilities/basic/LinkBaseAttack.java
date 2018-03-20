package game.common.abilities.basic;

import game.common.abilities.AbstractAbility;

public class LinkBaseAttack extends AbstractAbility{

	//Image displaying the ability
	//Seconds
	
	public LinkBaseAttack(String name, float cooldown, float damage) {
		super(name, cooldown, damage);
	}
	
	@Override
	public float dealDamage() {
		return getDamage();
	}	
}
