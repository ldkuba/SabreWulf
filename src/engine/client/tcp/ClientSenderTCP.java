package engine.client.tcp;

import engine.server.core.Player;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientSenderTCP extends Thread{

	Socket CSSocket = null;
	game.client.Client client = null;
	ObjectOutputStream oos = null;

	public ClientSenderTCP(Socket CSSocket, game.client.Client client){
		this.client = client;
		this.CSSocket = CSSocket;

	}

	public void run(){

		try {

			oos = new ObjectOutputStream(CSSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!CSSocket.isClosed()){
			try {
				Thread.currentThread().sleep(100);
				oos.writeObject(client.abs.take());
			} catch (SocketException se){
				try
				{
					CSSocket.close();
				}catch (IOException e)
				{
					//e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
