package game.common.logic.actions;

import engine.maths.Vec2;
import engine.maths.Vec3;

import javax.swing.*;

public class Movement {
	

	public Vec3 moveTo(Vec3 coordPlayer, Vec3 coordEnemy) {
		float toX = coordPlayer.getX() - coordEnemy.getX();
		float toY = coordPlayer.getY() - coordEnemy.getY();
		Vec3 toPos = new Vec3(toX, toY,coordPlayer.getZ());

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
