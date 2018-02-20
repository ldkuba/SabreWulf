package game.common.items.attributes.base;

import game.common.items.attributes.Attribute;

public class AttackDamage extends Attribute{

	private int attackDmg;
	
	public AttackDamage(int dmg) {
		attackDmg = dmg;
	}
	
	public int getValue() {
		return attackDmg;
	}
	
}
