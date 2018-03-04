package game.common.logic.actions;

import engine.logic.AbstractAction;
import engine.maths.Vec2;

public class Movement extends AbstractAction{
	

	public Vec2 moveTo(Vec2 coordPlayer, Vec2 coordEnemy) {
		float toX = coordPlayer.getX() - coordEnemy.getX();
		float toY = coordPlayer.getY() - coordEnemy.getY();
		Vec2 toPos = new Vec2(toX, toY);

		return toPos;
	}
	
	public float move() {
		/*
		 * Physics
		 * and moving the player.
		 */
		
		return 0.0f;
	}
	
	
	
}
