package game.client;

import java.util.ArrayList;

import engine.client.udp.*;
import engine.client.core.ClientConnection;
import engine.common_net.AbstractMessage;
import engine.common_net.ConnectionListener;
import engine.common_net.MessageListener;

public class Client
{
	private ClientConnection connectClient;
	
	private ArrayList<MessageListener> messageListeners;
	private ArrayList<ConnectionListener> connectionListeners;
	
	public Client()
	{
		messageListeners = new ArrayList<>();
		connectionListeners = new ArrayList<>();
		
		connectClient = new ClientConnection(this);
		connectClient.start();
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
		for(ConnectionListener connListener : connectionListeners)
		{
			connListener.clientConnected(/* tbd */);
		}
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
		// TODO connectClient.sendTCP(msg);
	}
	
	public void sendUDP(AbstractMessage msg) // SEND OR BROADCAST, WHATEVER YOU WANNA CALL IT
	{
		// TODO connectClient.sendUDP(msg);
		
	}
}
