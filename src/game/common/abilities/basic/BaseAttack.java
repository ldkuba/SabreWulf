package game.common.abilities.basic;

import game.common.abilities.AbstractAbility;

public class BaseAttack extends AbstractAbility{

	//Image displaying the ability
	
	private int damage = 4;
	private float coolDown = 0.5f;		//Seconds
	
	@Override
	public int dealDamage() {
		return damage;
	}
	
	
}
