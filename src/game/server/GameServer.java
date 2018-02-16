package game.server;

import java.util.ArrayList;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.GameInstance;
import engine.net.server.core.Server;
import engine.net.server.core.Player;
import engine.net.server.core.ServerStateManager;
import game.server.global.GlobalServerConnectionListener;
import game.server.global.GlobalServerMessageListener;

public class GameServer
{
	private Server server;

	private GlobalServerConnectionListener connectionListener;
	private GlobalServerMessageListener messageListener;

	private ServerStateManager smg;

	public ArrayList<Player> players = null;

	public ArrayList<GameInstance> games = null;

	public int getNoPlayers() {
		return players.size();
	}

	public void addPlayer(Player player){
		players.add(player);
	}

	public void removePlayer(Player player){
		players.remove(player);
	}

	public GameServer()
	{

		connectionListener = new GlobalServerConnectionListener(this);
		messageListener = new GlobalServerMessageListener(this);
		players = new ArrayList<Player>(60);
		games = new ArrayList<GameInstance>(5);
		server = new Server(this);
		server.setName("GAME");
		server.start();
		games.add(new GameInstance());

		smg = new ServerStateManager(this);
		smg.setName("state manager");
		smg.start();
	}

	public void addMessage(AbstractMessage message, Player player){
			messageListener.addMessage(message,player);
			System.out.println("Received message");
	}

	public void addConnectionEvent(Player player, boolean connected){
			connectionListener.addConnectionEvent(connected,player);
	}

	public void handleMessagesAndConnections(){
		messageListener.handleMessageQueue();
		connectionListener.handleConnectionQueue();
	}

	public void sendTCP(AbstractMessage msg, Player p){
		p.addMsg(msg);
		System.out.println("Sending message");
	}

	public void broadcastTCP(AbstractMessage msg){
		for(int i = 0; i<players.size(); i++){
			sendTCP(msg, players.get(i));
		}
	}


	public void startUDPManager() {
		//server.startUDPManager();
	}
	
	public void sendUDP(AbstractMessage msg) // SEND OR BROADCAST, WHATEVER YOU WANNA CALL IT
	{
		//startUDPManager should be called before sending any UDP packets.
		
		// TODO server.sendUDP(msg);
		//server.sendUDP(msg);
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
