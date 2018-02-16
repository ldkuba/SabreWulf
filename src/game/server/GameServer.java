package game.server;

import java.util.ArrayList;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.*;
import game.server.global.GlobalServerConnectionListener;
import game.server.global.GlobalServerMessageListener;

public class GameServer
{
	private Server server;

	private GlobalServerConnectionListener connectionListener;
	private GlobalServerMessageListener messageListener;

	private PlayerCountManager pcm;

	public ArrayList<Player> players = null;

	public ArrayList<GameInstance> games = null;

	public int getNoPlayers() {
		return players.size();
	}

	synchronized public void addPlayer(Player player){
		players.add(player);
	}

	synchronized public void removePlayer(Player player){
		players.remove(player);
	}

	public GameServer()
	{
		connectionListener = new GlobalServerConnectionListener(this);
		messageListener = new GlobalServerMessageListener(this);
		players = new ArrayList<Player>(50);
		games = new ArrayList<GameInstance>(5);
		server = new Server(this);
		server.setName("Socket Connection Manager");
		server.start();

		games.add(new GameInstance(this));

		pcm= new PlayerCountManager(this);
		pcm.setName("Player Count Manager");
		pcm.start();

	}

	public void addMessage(AbstractMessage message, Player player){
			messageListener.receiveMessage(message,player);
			System.out.println("Received message");
	}

	public void addConnectionEvent(Player player, boolean status){
		if(status){
			connectionListener.clientConnected(player);
		}
		else{
			connectionListener.clientDisconnected(player);
		}
	}

	public void sendTCP(AbstractMessage msg, Player destination){
		destination.addMsg(msg);
	}

	public void broadcastTCP(AbstractMessage msg, ArrayList<Player> destination){
		for(int i = 0; i<destination.size(); i++){
			sendTCP(msg, destination.get(i));
		}
	}

	public static void main(String[] args)
	{
		GameServer gameServerMain = new GameServer();
	}

	public boolean isFreeGameInstance(){
		if(games.size()<=10){
			for (int i = 0; i < games.size(); i++) {
				if(!games.get(i).isFull()){
					return true;
				}
			}
		}
		return false;
	}

	public GameInstance getFreeGameInstance(){
		for(int i=0; i<games.size(); i++){
			if(!games.get(i).isFull()){
				return games.get(i);
			}
		}
		return null;
	}
}
