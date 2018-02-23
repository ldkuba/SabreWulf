package game.logic;

import engine.maths.Vec2;

public class Respawn {

	private Player myPlayer;
	Vec2 spawnCoord = new Vec2(0, 0); // zero to be replaced by coords of spawn
										// room

	public Respawn() {

	}

	public boolean zeroVitality() {
		if (myPlayer.getVitality <= 0) {
			return true;
		}
	}

	public Vec2 respawn() {
		if (zeroVitality() == true) {
			myPlayer.setCoord(spawnCoord);
		}
	}
}
