package engine.client.tcp;

import engine.common_net.TCPTalks_trial;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSenderTCP extends Thread{

	Socket CSSocket = null;
	game.client.Client client = null;
	ObjectOutputStream oos = null;
	TCPTalks_trial trial=null;

	public ClientSenderTCP(Socket CSSocket, game.client.Client client) throws IOException {
		this.client = client;
		this.CSSocket = CSSocket;
		oos = new ObjectOutputStream(CSSocket.getOutputStream());
		trial = new TCPTalks_trial(0,0);

	}

	public void run(){
		while(true){
			try {
				oos.writeObject(trial);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
