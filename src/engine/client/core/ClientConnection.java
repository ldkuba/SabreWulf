package engine.client.core;

import engine.client.tcp.ClientReceiverTCP;
import engine.client.tcp.ClientSenderTCP;

public class ClientConnection extends Thread{
	
	private static String playerName;
	private static ClientReceiverTCP CReceiver;
	private static ClientSenderTCP CSender;
	
	public ClientConnection() {
		
	}
	
	public ClientConnection(String name) {
		playerName = name;
	}
	
	public void run() {
		CReceiver = new ClientReceiverTCP();
		CSender = new ClientSenderTCP();
		//Start TCP connection with the server
		CReceiver.start();
		CSender.start();
	}
	
}
