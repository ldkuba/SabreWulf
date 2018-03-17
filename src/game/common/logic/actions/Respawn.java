package game.common.logic.actions;

import engine.maths.Vec2;
import game.common.actors.Player;

public class Respawn {

	Vec2 spawnCoord = new Vec2(0, 0); // zero to be replaced by coords of
	
	public boolean alive(Player myActor) {
		if (zeroHealth(myActor)) {
			respawn(myActor);
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

	private void respawn(Player myActor) {
			// do a cooldown timer
			// myActor.setPosition(spawnCoord);
		
		// some way to set the player health to max
	}
}
