package game.common.player;

import java.util.ArrayList;

import engine.scene.Scene;
import game.common.actors.Player;

public class PlayerManager
{
	private ArrayList<Player> players;
	private Scene scene;
	
	public PlayerManager(Scene scene)
	{
		players = new ArrayList<Player>();
		this.scene = scene;
	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
		scene.addEntity(player.getEntity());
	}
	
	public Player getPlayer(int id)
	{
		for(Player player : players)
		{
			if(id == player.getPlayerId())
			{
				return player;
			}
		}
		
		return null;
	}
	
	public void update()
	{
		for(Player player : players)
		{
			player.update();
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
