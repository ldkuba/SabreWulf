package engine.net.client;

import engine.net.common_net.ConnectionListener;
import engine.net.server.core.Player;

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
