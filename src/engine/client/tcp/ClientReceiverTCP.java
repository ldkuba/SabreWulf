package engine.client.tcp;

import engine.common_net.AbstractMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiverTCP extends Thread{
	Socket CSSocket = null;
	game.client.Client client = null;
	ObjectInputStream ois = null;

	public ClientReceiverTCP(Socket CSSocket, game.client.Client client){
	    this.client = client;
	    this.CSSocket = CSSocket;
	}

	public void run() {

		try {
			ois = new ObjectInputStream(CSSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}


		while(true){

			try {
				AbstractMessage abs = (AbstractMessage) ois.readObject();
				if(abs != null)
				client.notifyMessageListeners(abs);
			}catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}

}