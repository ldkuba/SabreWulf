package engine.client.core;

import engine.client.tcp.ClientReceiverTCP;
import engine.client.tcp.ClientSenderTCP;
import engine.client.udp.ClientBroadcastReceiverUDP;
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
	
	protected ClientSenderUDP udpSender;
	protected ClientBroadcastReceiverUDP udpReceiver;
	
	private Socket CSSocket=null; //Client-Server Socket TCP


	public void run(){
		try {
			CSSocket = new Socket("localhost",4445);
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
	
	public void startUDPSender(int portSender, String serverAddress, int packetSize) {
		udpSender = new ClientSenderUDP(portSender, serverAddress, packetSize);
		//*TO-DO: Add checks.
	}
	
	public void startUDPReceiver(int groupPort, String groupID) {
		udpReceiver = new ClientBroadcastReceiverUDP(groupPort, groupID);
		//TO-DO: Add checks.
	}
	
	public void sendUDP(AbstractMessage msg) {
		udpSender.addMessage(msg);
	}
}

