package engine.client.core;

import engine.client.tcp.ClientReceiverTCP;
import engine.client.tcp.ClientSenderTCP;
import game.client.Client;

public class ClientConnection extends Thread{
	
	private static String playerName;
	private static ClientReceiverTCP CReceiver;
	private static ClientSenderTCP CSender;
	
	private Client client; // for informing the listeners with notifyXXListeners();
	
	public ClientConnection(Client client) {
		this.client = client;
	}
	
	public ClientConnection(Client client, String name) {
		playerName = name;
		this.client = client;
	}
	
	public void run() {
		CReceiver = new ClientReceiverTCP();
		CSender = new ClientSenderTCP();
		//Start TCP connection with the server
		CReceiver.start();
		CSender.start();
	}
	
}
