package game.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import engine.common_net.AbstractMessage;
import engine.common_net.PeerList;
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
		players = new ArrayList<Player>(6);
		gameServer = new GameServer(this);
		gameServer.setName("GAME");
		gameServer.start();


		smg = new ServerStateManager(this);
		smg.setName("state manager");
		smg.start();
	}

	public void notifyMessageListeners(AbstractMessage msg)
	{
		messageListener.receiveMessage(msg);
	}
	
	public void notifyConnectionListenersConnected(Player player)
	{
		connectionListener.clientConnected(player);
	}
	
	public void notifyConnectionListenersDisconnected(Player player)
	{
		connectionListener.clientDisconnected(player);
	}
	
	public void sendTCP(PeerList msg, Player p){
		p.addMsg(msg);
	}

	public void broadcastTCP(PeerList msg, ArrayList<Player> players){
		for(int i = 0; i<players.size(); i++){
			sendTCP(msg, players.get(i));
		}
	}


	public void startUDPManager() {
		gameServer.startUDPManager();
	}
	
	public void sendUDP(AbstractMessage msg) // SEND OR BROADCAST, WHATEVER YOU WANNA CALL IT
	{
		//startUDPManager should be called before sending any UDP packets.
		
		// TODO gameServer.sendUDP(msg);
		gameServer.sendUDP(msg);
	}
	
	public static void main(String[] args)
	{
		Server serverMain = new Server();
	}
}