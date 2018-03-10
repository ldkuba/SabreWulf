
package game.common.logic.actions;

import engine.application.Timer;
import engine.maths.Vec2;
import game.common.actors.Actor;
import game.common.actors.Player;

public class Respawn {

	Timer timer;
	
	public boolean isAlive(Actor myActor) {
		if (myActor.getHealth() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public void respawn(Actor myActor) {
			timer = new Timer(200); // 10 sec respawn cooldown
			
			myActor.setPosition(myActor.getBase());
			myActor.setHealth(myActor.getHealthLimit());
	}
}
