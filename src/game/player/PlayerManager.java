package game.player;

import java.util.ArrayList;

//For managing which players are issued which instructions
public class PlayerManager {
	
	protected ArrayList<Player> players;
	
	public PlayerManager()
	{
		players = new ArrayList<>();
	}
	
	public void getPlayerStatus(Player local){
		local.getPosition();
		local.getHealth();
		local.getEnergy();
		local.getStatus();
	}

	public void getStatuses() {
		for(int i = 0; i < players.size(); i++){
			getPlayerStatus(players.get(i));
		}
	}
}
