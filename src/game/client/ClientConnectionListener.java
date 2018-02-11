package game.client;

import engine.common_net.ConnectionListener;
import game.networking.ConnectionMessage;

import java.net.Socket;

public class ClientConnectionListener implements ConnectionListener
{
	private Client client;

	ClientConnectionListener(Client client){
		this.client=client;
	}
	@Override
	public void clientConnected(Socket socket)
	{
		ConnectionMessage conn = new ConnectionMessage();
		conn.setName("player1");
		client.abs.add(conn);
	}

	@Override
	public void clientDisconnected(Socket socket)
	{
		//do other shit
	}
}
