package game.client;

import engine.common_net.ConnectionListener;
import engine.server.core.Player;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionListener implements ConnectionListener
{
	private Client client;

	ClientConnectionListener(Client client){
		this.client=client;
	}

	@Override
	public void clientConnected(Player player){
		
	}

	@Override
	public void clientDisconnected(Player player) {
		client.getMain().exit();
	}
}
