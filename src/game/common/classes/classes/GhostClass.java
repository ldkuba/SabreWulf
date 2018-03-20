package game.common.classes.classes;

import game.common.abilities.basic.GhostBaseAttack;
import game.common.abilities.spells.SpecialAbility;
import game.common.classes.AbstractClass;
import game.common.logic.actions.Action;

import java.util.ArrayList;

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

		baseAttack = new GhostBaseAttack(0,0);

		abilities = new ArrayList<Action>();

		//Define abilities here

		resourcePath = "res/actors/ghost/";
	}

}
