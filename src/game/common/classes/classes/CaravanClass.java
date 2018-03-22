package game.common.classes.classes;

import game.common.classes.AbstractClass;
import game.common.logic.actions.Action;

import java.util.ArrayList;

/**
 * Creates a Caravan with all its values and abilities
 * Used to create a Caravan NPC
 *
 * @author SabreWulf
 */
public class CaravanClass extends AbstractClass{

    public CaravanClass() {

        //Base Stats
        health = 1.0f;
        moveSpeed = 0.02f;  //Speed of caravan
        damage = 1.0f;
        resistance = 1.0f;
        energy = 1.0f;
        attackRange = 1.0f;

        //Animation Values
        moveAnimationLength = 2;
        moveAnimationLeft = 7;
        moveAnimationRight = 4;
        moveAnimationUp = 10;
        moveAnimationDown = 13;

        //Regeneration values
        energyReg = 0.0f;
        healthReg = 0.0f;

        //set Base attack
        baseAttack = null;

        //set abilities
        abilities = new ArrayList<Action>();

        //Define abilities here

        resourcePath = "res/actors/caravan/";
    }

}
