package game.server;

import java.util.ArrayList;

import engine.common_net.AbstractMessage;
import engine.common_net.ConnectionListener;
import engine.common_net.MessageListener;
import engine.server.core.GameServer;

public class Server
{
	private GameServer gameServer;
	
	private ArrayList<MessageListener> messageListeners;
	private ArrayList<ConnectionListener> connectionListeners;
	
	public Server()
	{
		gameServer = new GameServer(this);
		gameServer.setName("GAME");
		gameServer.start();
	}
	
	public void registerMessageListener(MessageListener msgL)
	{
		messageListeners.add(msgL);
	}
	

	public void registerConnectionListener(ConnectionListener connL)
	{
		connectionListeners.add(connL);
	}
	
	public void notifyMessageListeners(AbstractMessage msg)
	{
		for(MessageListener msgListener : messageListeners)
		{
			msgListener.receiveMessage(msg);
		}
	}
	
	public void notifyConnectionListenersConnected(/*Format to be decided*/)
	{
		//for(ConnectionListener connListener : connectionListeners)
		{
			//connListener.clientConnected(/* tbd */);
		}
		//
	}
	
	public void notifyConnectionListenersDisconnected(/*Format to be decided*/)
	{
		for(ConnectionListener connListener : connectionListeners)
		{
			connListener.clientDisconnected(/* tbd */);
		}
	}
	
	public void sendTCP(AbstractMessage msg) // OPTIONALLY ADD A PARAMETER TO SEND TO A SPECIFIC CLIENT
	{
		// TODO gameServer.sendTCP(msg);
	}
	
	public void sendUDP(AbstractMessage msg) // SEND OR BROADCAST, WHATEVER YOU WANNA CALL IT
	{
		// TODO gameServer.sendUDP(msg);
	}
	
	public static void main(String[] args)
	{
		Server serverMain = new Server();
	}
}