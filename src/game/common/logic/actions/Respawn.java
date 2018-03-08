
package game.common.logic.actions;

import engine.application.Timer;
import engine.maths.Vec2;
import engine.maths.Vec3;
import game.common.actors.Actor;
import game.common.actors.Player;

public class Respawn {

	Vec3 spawnCoord = new Vec3(0, 0,0); // zero to be replaced by coords of base
	Timer timer;
	
	public boolean alive(Actor myActor) {
		if (myActor.getHealth() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean zeroHealth(Player myActor) {
		if (myActor.getHealth() <= 0) {
			return true;
		}
		return false;
	}

	public void respawn(Actor myActor) {
			// do a cooldown timer
			myActor.setPosition(spawnCoord);
			timer = new Timer(200);
		// some way to set the player health to max
	}
}
