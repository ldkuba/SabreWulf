package game.client;

import engine.common_net.ConnectionListener;

import java.net.Socket;

public class ClientConnectionListener implements ConnectionListener
{
	@Override
	public void clientConnected(Socket socket)
	{
		// do shit 
	}

	@Override
	public void clientDisconnected(Socket socket)
	{
		//do other shit
	}
}
