package game.server;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.*;
import game.server.global.GlobalServerConnectionListener;
import game.server.global.GlobalServerMessageListener;

/**
 * Class for the game server
 * @author User
 *
 */
public class GameServer
{
	private Server server;

	private GlobalServerConnectionListener connectionListener;
	private GlobalServerMessageListener messageListener;

	private PlayerCountManager pcm;

	public ArrayList<NetPlayer> players;

	private CopyOnWriteArrayList<GameInstance> games;

	/**
	 * Gets the number of players currently on the server
	 * @return
	 */
	public int getNoPlayers() {
		return players.size();
	}

	/**
	 * Adds a player to the server
	 * @param player
	 */
	synchronized public void addPlayer(NetPlayer player){
		player.setPlayerId(players.size());
		players.add(player);
	}

	/** 
	 * Removes a player from the server
	 * @param player
	 */
	synchronized public void removePlayer(NetPlayer player){
		games.get(player.getCurrentGame()).removePlayer(player);
		players.remove(player);
	}

	/**
	 * Constructs the game server
	 */
	public GameServer()
	{
		connectionListener = new GlobalServerConnectionListener(this);
		messageListener = new GlobalServerMessageListener(this);

		players = new ArrayList<NetPlayer>(50);
		games = new CopyOnWriteArrayList<GameInstance>();

		server = new Server(this);
		server.setName("Socket Connection Manager");
		server.start();

		pcm= new PlayerCountManager(this);
		pcm.setName("Player Count Manager");
		pcm.start();
	}

	public GameInstance createGameInstance() {
		GameInstance gi = new GameInstance(this, games.size());
		games.add(gi);
		return gi;
	}

	public CopyOnWriteArrayList<GameInstance> getGames() {
		return games;
	}

	public void addMessage(AbstractMessage message, NetPlayer player){
		if(player.getCurrentGame()==-1){
			messageListener.receiveMessage(message,player);
		}
		else{
			games.get(player.getCurrentGame()).getGIManager().getGameEngine().getNetworkManager().addMessage(message, player);
		}
	}

	public void addConnectionEvent(NetPlayer player, boolean status){
		if(player.getCurrentGame()==-1){
			//maybe Concurrency issues
			if(status){

				connectionListener.clientConnected(player);
			}
			else{
				connectionListener.clientDisconnected(player);
			}
		}
		else{
			games.get(player.getCurrentGame()).getGIManager().getGameEngine().getNetworkManager().addConnectionEvent(player, status);
		}
	}

	public void sendTCP(AbstractMessage msg, NetPlayer destination){
		destination.addMsg(msg);
	}

	public void broadcastTCP(AbstractMessage msg, ArrayList<NetPlayer> destination){
		for(int i = 0; i<destination.size(); i++){
			sendTCP(msg, destination.get(i));
		}
	}

	/**
	 * Main method to start the game server
	 * @param args
	 */
	public static void main(String[] args)
	{
		GameServer gameServerMain = new GameServer();
	}

	synchronized public boolean isFreeGameInstance(){
		if(games.size()<=10){
			return true;
		}
		return false;
	}

	synchronized public GameInstance getFreeGameInstance(){
		for(int i=0; i<games.size(); i++){
			if(!games.get(i).isFull() && games.get(i).isAvailable()){
				return games.get(i);
			}
		}
		return null;
	}

	public void removeGameInstance(GameInstance instance){
		games.remove(instance);
	}

}
