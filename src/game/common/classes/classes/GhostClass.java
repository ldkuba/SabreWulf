package game.common.classes.classes;

import game.common.abilities.basic.BaseAttack;
import game.common.abilities.spells.SpecialAbility;
import game.common.classes.AbstractClass;

/**
 * Creates the Ghost role with its own values.
 */

public class GhostClass extends AbstractClass {
	
	public GhostClass() {
		health = 140;
		moveSpeed = 0.08f;
		damage = 60;
		resistance = 55;
		energy = 100;
		attackRange = 2.0f;
		
		moveAnimationLength = 2;
		moveAnimationLeft = 7;
		moveAnimationRight = 4;
		moveAnimationUp = 10;
		moveAnimationDown = 13;
		
		energyReg = 0.2f;
		healthReg = 0.4f;

		baseAttack = new BaseAttack("Boo",0.2f,0.0f);

		/**
		 * Special Ability One:
		 * 		'Haunt'
		 * 	Decreases resistance by 20%
		 * 	Decreases movement by 50%
		 * 	Decreases Energy by 20%
		 * 	Decreases Damage by 10%
		 *
		 * 	Duration: 3 seconds
		 *
		 */
		SpecialAbility specialAbilityOne = new SpecialAbility("Haunt",1.0f,0.0f);
		specialAbilityOne.setResistance(0.8f);
		specialAbilityOne.setMovement(0.5f);
		specialAbilityOne.setEnergy(0.8f);
		specialAbilityOne.setDamage(0.9f);
		specialAbilityOne.setDuration(0.3f);

		specialAbilities.add(specialAbilityOne);

		resourcePath = "res/actors/ghost/";
	}

}
