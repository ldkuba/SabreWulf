package engine.net.common_net;

import engine.net.server.core.Player;

public interface ConnectionListener
{
	public void clientConnected(Player player);
	
	public void clientDisconnected(Player player);
}
