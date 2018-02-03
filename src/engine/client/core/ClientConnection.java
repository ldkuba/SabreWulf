package engine.client.core;

import engine.client.tcp.ClientReceiverTCP;
import engine.client.tcp.ClientSenderTCP;
import game.client.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread{
	
	private static String playerName;
	private static ClientReceiverTCP CReceiver;
	private static ClientSenderTCP CSender;
	
	private Client client; // for informing the listeners with notifyXXListeners();
	
	public ClientConnection(Client client) {
		this.client = client;
	}

	Socket CSSocket=null; //Client-Server Socket TCP
	public void run(){
		try {
			CSSocket = new Socket("localhost",5800);
			client.notifyConnectionListenersConnected();

		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true);
	}
}

