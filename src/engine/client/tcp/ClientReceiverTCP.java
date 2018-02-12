package engine.client.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import engine.common_net.AbstractMessage;

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


		while(!CSSocket.isClosed()){

			try {
				AbstractMessage abs = (AbstractMessage) ois.readObject();
				if(abs != null)
				client.notifyMessageListeners(abs);
			} catch (SocketException ex)
			{
				try
				{
					CSSocket.close();
				}catch (IOException e)
				{
					// TODO Auto-generated catch block
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