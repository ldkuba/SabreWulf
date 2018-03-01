package game.common.logic.actions;

import engine.maths.Vec2;
import game.common.actors.Player;

public class Respawn {

	private Player myPlayer;
	Vec2 spawnCoord = new Vec2(0, 0); // zero to be replaced by coords of
										// spawn
										// room

	public Respawn() {

	}

	public boolean zeroHealth() {
		if (myPlayer.getHealth() <= 0) {
			return true;
		}
		return false;
	}

	public void respawn() {
		if (zeroHealth() == true) {
			// do a cooldown timer
			myPlayer.setPosition(spawnCoord);
		}
		// some way to set the player health to max
	}
}
