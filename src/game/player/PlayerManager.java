package game.player;

import java.util.ArrayList;

import engine.scene.Scene;

//For managing which players are issued which instructions
public class PlayerManager {
	
	protected ArrayList<Player> players;
	private Scene scene;
	
	public PlayerManager(Scene scene)
	{
		players = new ArrayList<>();
		scene = this.scene;
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
	
	public Player getPlayer(int id){
		Player correctId = null;
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).checkID() == id){
				correctId = players.get(i);
			}
		}
		return correctId;
	}
	
	public Player getLocalPlayer(){
		Player local = null;
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).checkLocal()){
				local = players.get(i);
			}
		}
		return local;
	}
	
	public void render(){
		
	}
}
