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

public class Client
{
	private ClientConnection connectClient;
	private ClientMessageListener messageListener;
	private ClientConnectionListener connectionListener;
	
	private Main app;

	public BlockingQueue<AbstractMessage> abs = new LinkedBlockingQueue<AbstractMessage>(100);
	
	public Client(Main app)
	{
		this.app = app;
		messageListener = new ClientMessageListener(app);
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
			connectClient.closeSocket();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			connectClient.join();
		}catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Main getMain()
	{
		return app;
	}
}
