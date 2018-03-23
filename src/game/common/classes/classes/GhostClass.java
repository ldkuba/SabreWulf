package game.common.classes.classes;

import game.common.abilities.basic.GhostBaseAttack;
import game.common.classes.AbstractClass;
import game.common.logic.actions.Action;

import java.util.ArrayList;

/**
 * Creates the Ghost role with its own values, as well as its abilities.
 * Used by the Actor when role is chosen in lobby.
 *
 * @author SabreWulf
 */

public class GhostClass extends AbstractClass {
	
	public GhostClass() {

		//Base Stats
		health = 140;
		moveSpeed = 0.08f;
		damage = 60;
		resistance = 55;
		energy = 100;
		attackRange = 2.0f;

		//Animation Values
		moveAnimationLength = 2;
		moveAnimationLeft = 7;
		moveAnimationRight = 4;
		moveAnimationUp = 10;
		moveAnimationDown = 13;

		//Regeneration values
		energyReg = 0.2f;
		healthReg = 0.4f;

		//set Base attack
		baseAttack = new GhostBaseAttack(0,0);

		//set abilities
		abilities = new ArrayList<Action>();

		//Define abilities here

		resourcePath = "res/actors/ghost/";
	}

}
