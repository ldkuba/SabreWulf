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

	public ClientSenderTCP(Socket CSSocket, game.client.Client client){
		this.client = client;
		this.CSSocket = CSSocket;
		trial = new TCPTalks_trial(0,0);

	}

	public void run(){

		try {

			oos = new ObjectOutputStream(CSSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true){
			try {
				Thread.currentThread().sleep(100);
				oos.writeObject(client.abs.take());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
