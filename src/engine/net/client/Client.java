package engine.net.client;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.net.client.core.ClientConnection;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;
import game.client.Main;
import engine.net.common_net.networking_messages.QuitMessage;

public class Client
{
	private ClientConnection connectClient;
	// Local blocking queue of messages that will get sent by the Sender thread
	public BlockingQueue<AbstractMessage> abs = new LinkedBlockingQueue<AbstractMessage>(100);
	private Main main;
	// Initiating connection listener
	public Client(Main main)
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

	public Main getMain(){
		return main;
	}
}
