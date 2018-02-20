package engine.net.client.core;

import engine.net.client.tcp.ClientReceiverTCP;
import engine.net.client.tcp.ClientSenderTCP;
import engine.net.client.Client;
import game.common.config;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread{
	
	private Client client; // for informing the listeners with notifyXXListeners();
	
	public ClientConnection(Client client) {
		this.client = client;
	}
	private ClientSenderTCP csender = null;
	private ClientReceiverTCP creceiver = null;

	
	private Socket CSSocket=null; //Client-GameServer Socket TCP

	public void run(){
		try {
			CSSocket = new Socket(config.host,config.TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

		csender = new ClientSenderTCP(CSSocket, client);
		csender.setName("TCPSender");
		csender.start();

		creceiver = new ClientReceiverTCP(CSSocket, client);
		creceiver.setName("TCPReceiver");
		creceiver.start();
	}

	public void closeSocket() throws IOException
	{
		CSSocket.close();
	}
}

