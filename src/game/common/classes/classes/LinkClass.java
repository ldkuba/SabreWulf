package game.common.classes.classes;

import game.common.abilities.basic.BaseAttack;
import game.common.abilities.spells.SpecialAbility;
import game.common.classes.AbstractClass;

public class LinkClass extends AbstractClass {
	
	public LinkClass() {
		health = 140;
		moveSpeed = 0.08f;
		damage = 60;
		resistance = 55;
		energy = 100;
		attackRange = 6.0f;
		
		moveAnimationLength = 2;
		moveAnimationLeft = 7;
		moveAnimationRight = 4;
		moveAnimationUp = 10;
		moveAnimationDown = 13;
		
		energyReg = 0.2f;
		healthReg = 0.4f;

		

		/**
		 * Special Ability One:
		 * 		'Fire Arrow'
		 * Duration: 4 seconds
		 * Decrease Movement by 10%
		 *
		 * Energy cost = 15.0f.
		 */

		SpecialAbility fireArrow = new SpecialAbility("Fire Arrow",0.5f,3.0f);
		fireArrow.setDuration(0.6f);
		fireArrow.setMovement(0.9f);
		fireArrow.setEnergyCost(15.0f);
		specialAbilities.add(fireArrow);

		resourcePath = "res/actors/link/";
	}
}
