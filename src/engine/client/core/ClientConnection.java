package engine.client.core;

import engine.client.tcp.ClientReceiverTCP;
import engine.client.tcp.ClientSenderTCP;
import game.client.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread{
	
	private Client client; // for informing the listeners with notifyXXListeners();
	
	public ClientConnection(Client client) {
		this.client = client;
	}
	private ClientSenderTCP csender = null;
	private ClientReceiverTCP creceiver = null;
	private Socket CSSocket=null; //Client-Server Socket TCP


	public void run(){
		try {
			CSSocket = new Socket("localhost",5800);
			client.notifyConnectionListenersConnected();
			this.creceiver = new ClientReceiverTCP(CSSocket, client);
			this.csender = new ClientSenderTCP(CSSocket, client);

		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true);
	}
}

