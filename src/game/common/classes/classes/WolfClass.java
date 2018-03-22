package game.common.classes.classes;

import game.common.abilities.basic.WolfBaseAttack;
import game.common.classes.AbstractClass;
import game.common.logic.actions.Action;

import java.util.ArrayList;

/**
 * Creates Ghost role with all its values and abilities
 * Used by the Actor when role is chosen in lobby.
 *
 * @author SabreWulf
 */

public class WolfClass extends AbstractClass {
	
	public WolfClass() {

		//Base stats
		health = 140;
		moveSpeed = 0.08f;
		damage = 60;
		resistance = 55;
		energy = 100;
		attackRange = 2.0f;

		//Animations
		moveAnimationLength = 2;
		moveAnimationLeft = 7;
		moveAnimationRight = 4;
		moveAnimationUp = 10;
		moveAnimationDown = 13;

		//Regeneration values
		energyReg = 0.2f;
		healthReg = 0.4f;

		//Set base attack
		baseAttack = new WolfBaseAttack(0,0);

		//Set abilities
		abilities = new ArrayList<Action>();

		//Define abilities here.
		
		resourcePath = "res/actors/wolf/";
	}
}
