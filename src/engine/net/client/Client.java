package engine.net.client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.application.Application;
import engine.net.client.core.ClientConnection;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.NetPlayer;
import game.client.Main;
import engine.net.common_net.networking_messages.QuitMessage;

public class Client
{
	private ClientConnection connectClient;
	// Local blocking queue of messages that will get sent by the Sender thread
	public BlockingQueue<AbstractMessage> abs = new LinkedBlockingQueue<AbstractMessage>(100);
	private Application main;
	// Initiating connection listener
	public Client(Application main)
	{
		this.main = main;
		connectClient = new ClientConnection(this);
		connectClient.start();
	}
	
	public void sendTCP(AbstractMessage msg)
	{
		abs.add(msg);
	}

	
	public void stop()
	{
		try {
			sendTCP(new QuitMessage());
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

	public Application getMain(){
		return main;
	}
}
