package engine.client.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceiverTCP extends Thread{
	Socket CSSocket = null;
	game.client.Client client = null;
	ObjectInputStream ios = null;

	public ClientReceiverTCP(Socket CSSocket, game.client.Client client) throws IOException {
	    this.client = client;
	    this.CSSocket = CSSocket;
	    ios = new ObjectInputStream(CSSocket.getInputStream());
	}

	public void run() {
		
	}

}
