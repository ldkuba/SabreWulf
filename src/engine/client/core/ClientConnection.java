package engine.client.core;

import engine.client.tcp.ClientReceiverTCP;
import engine.client.tcp.ClientSenderTCP;
import engine.client.udp.ClientSenderUDP;
import engine.common_net.AbstractMessage;
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
			CSSocket = new Socket("localhost",4446);
		} catch (IOException e) {
			e.printStackTrace();
		}

		csender = new ClientSenderTCP(CSSocket, client);
		csender.setName("sender");
		csender.start();

		creceiver = new ClientReceiverTCP(CSSocket, client);
		creceiver.setName("listener");
		creceiver.start();
	}

	public void closeSocket() throws IOException
	{
		CSSocket.close();
	}
}

