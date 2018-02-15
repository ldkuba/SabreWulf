package engine.client.tcp;

import engine.common_net.AbstractMessage;
import engine.server.core.QuitMessage;

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
				AbstractMessage msg = client.abs.take();
				if(msg instanceof QuitMessage){
					CSSocket.close();
				}
				else {
					oos.writeObject(msg);
				}

			} catch (SocketException se){
				try
				{
					CSSocket.close();
					client.notifyConnectionListenerDisconnect(null);
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
