package game.common.classes.classes;

import game.common.classes.AbstractClass;

public class LinkClass extends AbstractClass {
	
	public LinkClass() {
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
		
		resourcePath = "res/actors/link/";
	}
}
