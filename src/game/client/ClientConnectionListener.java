package engine.net.client.core;

import engine.net.client.Client;
import engine.net.common_net.ConnectionListener;
import engine.net.server.core.Player;

public class ClientConnectionListener implements ConnectionListener
{
	private Client client;

	public ClientConnectionListener(Client client){
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
