package engine.common_net;

import engine.server.core.Player;

import java.net.Socket;

public interface ConnectionListener
{
	public void clientConnected(Player player);
	
	public void clientDisconnected(Player player);
}
