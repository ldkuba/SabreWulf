package game.common.items.attack;

import game.common.items.AbstractItem;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.Strength;

//You have read The Five Rings. Giving insight in perfecting you strikes.

public class TheFiveRings extends AbstractItem{

	
	
	private Attribute attackDmg = new Strength(4);

	public int getAttackDmg() {
		return attackDmg;
	}
	
}
