package game.client;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import engine.client.core.ClientConnection;
import engine.common_net.AbstractMessage;
import engine.server.core.Player;
import game.Main;
import game.networking.InternalQuit;

public class Client
{
	private ClientConnection connectClient;
	private ClientMessageListener messageListener;
	private ClientConnectionListener connectionListener;
	
	private Main app;

	// Local blocking queue of messages that will get sent by the Sender thread
	public BlockingQueue<AbstractMessage> abs = new LinkedBlockingQueue<AbstractMessage>(100);

	// Initiating connection listener
	public Client(Main app)
	{
		this.app = app;
		messageListener = new ClientMessageListener(this);
		connectionListener = new ClientConnectionListener(this);
		
		connectClient = new ClientConnection(this);
		connectClient.start();
	}

	public void notifyMessageListeners(AbstractMessage msg, Player source)
	{
		messageListener.receiveMessage(msg, source);
	}
	
	public void notifyConnectionListenerConnect(Player player)
	{
		connectionListener.clientConnected(player);
	}
	
	public void notifyConnectionListenerDisconnect(Player player)
	{
		connectionListener.clientDisconnected(player);
	}
	
	public void sendTCP(AbstractMessage msg)
	{
		abs.add(msg);
	}
	
	public void sendUDP(AbstractMessage msg)
	{
		// TODO connectClient.sendUDP(msg);
		connectClient.sendUDP(msg);
	}
	
	public void stop()
	{
		try {
			abs.add(new InternalQuit());
			connectClient.closeSocket();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
		try {
			connectClient.join();
		}catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public Main getMain()
	{
		return app;
	}
}
