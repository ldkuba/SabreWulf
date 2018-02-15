package game.logic;

import engine.logic.AbstractAction;
import engine.maths.Vec2;

public class Attack extends AbstractAction{
	
	private Vec2 coordPlayer;
	private Vec2 coorEnemy;
	
	private Vec2 noMovement = new Vec2(0,0);
	
	public Vec2 attack(Vec2 playCoord, Vec2 coorEnemy) {
		
		if(inRange(playCoord, coorEnemy)) {
			doDamage();
			return noMovement;
		} else {
			return moveTo();
		}
		
	}

	public boolean inRange(Vec2 playerCoord, Vec2 enemyCoord) {
		
		/*
		 * Do some calculations
		 */
		
	}
	
	public void doDamage() {
		
	}
	
	public Vec2 moveTo() {
		
	}
	
}
