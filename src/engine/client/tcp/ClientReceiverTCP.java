package engine.client.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiverTCP extends Thread{
	Socket CSSocket = null;
	game.client.Client client = null;
	ObjectInputStream ois = null;

	public ClientReceiverTCP(Socket CSSocket, game.client.Client client) throws IOException {
	    this.client = client;
	    this.CSSocket = CSSocket;
	    ois = new ObjectInputStream(CSSocket.getInputStream());
	}

	public void run() {
		while(true){
			try {

				// Just sleeps for now

				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
