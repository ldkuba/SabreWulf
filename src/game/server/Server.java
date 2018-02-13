package game.server;

import java.util.ArrayList;

import engine.common_net.AbstractMessage;
import game.networking.PeerList;
import engine.server.core.GameServer;
import engine.server.core.Player;
import engine.server.core.ServerStateManager;

public class Server
{
	private GameServer gameServer;

	private ServerConnectionListener connectionListener;
	private ServerMessageListener messageListener;

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

	public Server()
	{

		connectionListener = new ServerConnectionListener(this);
		messageListener = new ServerMessageListener(this);
		players = new ArrayList<Player>(60);
		games = new ArrayList<GameInstance>(5);
		gameServer = new GameServer(this);
		gameServer.setName("GAME");
		gameServer.start();
		games.add(new GameInstance());

		smg = new ServerStateManager(this);
		smg.setName("state manager");
		smg.start();
	}

	public void notifyMessageListeners(AbstractMessage msg, Player player)
	{
		messageListener.receiveMessage(msg, player);
	}
	
	public void notifyConnectionListenersConnected(Player player)
	{
		connectionListener.clientConnected(player);
	}
	
	public void notifyConnectionListenersDisconnected(Player player)
	{
		connectionListener.clientDisconnected(player);
	}
	
	public void sendTCP(AbstractMessage msg, Player p){
		p.addMsg(msg);
	}

	public void informTheRest(AbstractMessage msg, Player p){
		for(int i = 0; i<players.size(); i++){
			if(!p.equals(players.get(i)))
			sendTCP(msg, players.get(i));
		}
	}

	public void broadcastTCP(AbstractMessage msg){
		for(int i = 0; i<players.size(); i++){
			sendTCP(msg, players.get(i));
		}
	}


	public void startUDPManager() {
		//gameServer.startUDPManager();
	}
	
	public void sendUDP(AbstractMessage msg) // SEND OR BROADCAST, WHATEVER YOU WANNA CALL IT
	{
		//startUDPManager should be called before sending any UDP packets.
		
		// TODO gameServer.sendUDP(msg);
		//gameServer.sendUDP(msg);
	}
	
	public static void main(String[] args)
	{
		Server serverMain = new Server();
	}

	public boolean isFreeGameInstance(){
		System.out.println("Hello world");
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
