package game.player;
//For managing which players are issued which instructions
public class PlayerManager {
	protected Player localplayer;
	protected Player[] players;
	
	public void getPlayerStatus(Player local){
		local.getPosition();
		local.getHealth();
		local.getEnergy();
		local.getStatus();
	}

	public void getStatuses(Player[] players) {
		for(int i = 0; i > players.length; i++){
			getPlayerStatus(players[i]);
		}
	}
}
