package game.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import engine.common_net.AbstractMessage;
import engine.common_net.ConnectionListener;
import engine.common_net.MessageListener;
import engine.server.core.GameServer;
import engine.server.core.Player;

public class Server
{
	private GameServer gameServer;

	private ServerConnectionListener connectionListener;
	private ServerMessageListener messageListener;

	private int connectionCount=0;

	ArrayList<Player> players;

	public void setConnectionCount(int connectionCount) {
		this.connectionCount = connectionCount;
	}

	public int getConnectionCount() {
		return connectionCount;
	}

	public void addPlayer(Player player){
		players.add(player);
	}

	public void removePlayer(){
		connectionCount--;
	}

	public Server()
	{
		connectionListener = new ServerConnectionListener(this);
		messageListener = new ServerMessageListener(this);
		players = new ArrayList<Player>();
		gameServer = new GameServer(this);
		gameServer.setName("GAME");
		gameServer.start();
	}

	public void notifyMessageListeners(AbstractMessage msg)
	{
		messageListener.receiveMessage(msg);
	}
	
	public void notifyConnectionListenersConnected(Socket socket)
	{
		connectionListener.clientConnected(socket);
	}
	
	public void notifyConnectionListenersDisconnected(Socket socket)
	{
		connectionListener.clientDisconnected(socket);
	}
	
	public void sendTCP(AbstractMessage msg, Player player){

	}

	public void broadcastTCP(AbstractMessage msg, ArrayList<Player> players){
		for(int i = 1; i<=players.size(); i++){
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