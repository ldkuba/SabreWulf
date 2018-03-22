package game.common.classes.classes;

import game.common.abilities.basic.LinkBaseAttack;
import game.common.classes.AbstractClass;
import game.common.logic.actions.Action;

import java.util.ArrayList;

/**
 * Creates the Link role with all its values and abilities
 * Used by the Actor when role is chosen in lobby.
 *
 *@author SabreWulf
 */

public class LinkClass extends AbstractClass {
	
	public LinkClass() {

		//Base stats
		health = 140;
		moveSpeed = 0.08f;
		damage = 60;
		resistance = 55;
		energy = 100;
		attackRange = 6.0f;

		//Animations
		moveAnimationLength = 2;
		moveAnimationLeft = 7;
		moveAnimationRight = 4;
		moveAnimationUp = 10;
		moveAnimationDown = 13;

		//Regeneration values
		energyReg = 0.2f;
		healthReg = 0.4f;

		//Set Base attack
		baseAttack = new LinkBaseAttack(0,0);

		//Set abilities
		abilities = new ArrayList<Action>();

		//Define abilities here.
		//Action increaseDamage = new Action(0);

		resourcePath = "res/actors/link/";
	}
}
