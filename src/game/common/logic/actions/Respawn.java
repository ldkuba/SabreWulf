
package game.common.logic.actions;

import engine.application.Timer;
import engine.maths.Vec2;
import engine.maths.Vec3;
import game.common.actors.Actor;
import game.common.actors.Mob;
import game.common.actors.Player;

public class Respawn {


	Vec3 spawnCoord = new Vec3(0, 0,0); // zero to be replaced by coords of base

	Timer timer;
	
	public boolean isAlive(Player myActor) {
		if (myActor.getHealth() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isAlive(Mob myNpc) {
		if (myNpc.getHealth() <= 0) {
			return false;
		}
		else {
			return true;
		}
	}

	public void respawn(Actor myActor) {
			timer = new Timer(200); // 10 sec respawn cooldown
			
			myActor.setPosition(myActor.getBase());
			myActor.setHealth(myActor.getHealthLimit());
	}
	
	public void respawn(Mob myNpc) {
		timer = new Timer(200); // 10 sec respawn cooldown
		
		myNpc.setPosition(myNpc.getStartPosition());
		myNpc.setHealth(myNpc.getHealthLimit());
	}
}

