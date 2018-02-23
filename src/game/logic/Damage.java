package game.logic;

import engine.net.server.core.Player;

public class Damage {

	public int lostVitality(Player myPlayer, Player opponent) {
		int myVitality = myPlayer.getVitality();
		myPlayer.setVitality(myVitality - (opponent.attack()));
		return 0;
	}

}
