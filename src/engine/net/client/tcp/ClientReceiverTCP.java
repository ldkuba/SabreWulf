package engine.net.client.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import engine.net.client.Client;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.LobbyUpdateMessage;

public class ClientReceiverTCP extends Thread{
	Socket CSSocket = null;
	Client client = null;
	ObjectInputStream ois = null;

	public ClientReceiverTCP(Socket CSSocket, Client client){
	    this.client = client;
	    this.CSSocket = CSSocket;
	}

	public void run() {

		try {
			ois = new ObjectInputStream(CSSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!CSSocket.isClosed()){
			try {
				AbstractMessage abs = (AbstractMessage) ois.readObject();
				if(abs != null) {
					client.getMain().getNetworkManager().addMessage(abs, null);
				}

			} catch (SocketException ex){
				try
				{
					CSSocket.close();
				}catch (IOException e)
				{
					e.printStackTrace();
				}
			}catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

}