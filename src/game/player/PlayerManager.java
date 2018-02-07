package game.player;

import java.util.ArrayList;

//For managing which players are issued which instructions
public class PlayerManager {
	protected Player localplayer;
	protected ArrayList<Player> players;
	
	public void getPlayerStatus(Player local){
		local.getPosition();
		local.getHealth();
		local.getEnergy();
		local.getStatus();
		
		players = new ArrayList<>();
	}

	public void getStatuses(ArrayList<Player> players) {
		for(int i = 0; i > players.size(); i++){
			getPlayerStatus(players.get(i));
		}
	}
}
