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
			//client.notifyConnectionListenersConnected();




		} catch (IOException e) {
			e.printStackTrace();
		}

		csender = new ClientSenderTCP(CSSocket, client);
		csender.setName("sender");
		csender.start();



		creceiver = new ClientReceiverTCP(CSSocket, client);
		creceiver.setName("listener");
		creceiver.start();






		while(true){
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

