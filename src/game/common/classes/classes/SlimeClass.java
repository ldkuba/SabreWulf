package game.common.classes.classes;

import java.util.ArrayList;

import game.common.abilities.basic.SlimeBaseAttack;
import game.common.classes.AbstractClass;
import game.common.logic.actions.Action;

/**
 * Creates Slime role with all its values and abilities
 * Used to create a Slime NPC
 *
 * @author SabreWulf
 */
public class SlimeClass extends AbstractClass {

	public SlimeClass() {
		
		//Base stats
		health = 100;
		moveSpeed = 0.08f;
		damage = 40;
		resistance = 35;
		energy = 60;
		attackRange = 2.0f;
		
		//Animations
		moveAnimationLength = 2;
		moveAnimationLeft = 7;
		moveAnimationRight = 4;
		moveAnimationUp = 10;
		moveAnimationDown = 13;
		
		//Regeneration values
		energyReg = 0.1f;
		healthReg = 0.2f;
		
		//Set base attack
		baseAttack = new SlimeBaseAttack(0,0);

		//Set abilities
		abilities = new ArrayList<Action>();

		//Define abilities here.
				
		resourcePath = "res/actors/slime/";
	}

}
