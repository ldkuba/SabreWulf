package engine.net.client.tcp;

import engine.net.client.Client;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.QuitMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientSenderTCP extends Thread{

	Socket CSSocket = null;
	Client client = null;
	ObjectOutputStream oos = null;

	public ClientSenderTCP(Socket CSSocket, Client client){
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
//				System.out.println(msg);
				if(msg instanceof QuitMessage){
					CSSocket.close();
				}
				else {
					oos.writeObject(msg);
					oos.reset();
				}

			} catch (SocketException se){
				try
				{
					CSSocket.close();
					client.getMain().getNetworkManager().addConnectionEvent(null, false);
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
