package engine.net.common_net;

import engine.net.common_net.networking_messages.ConnectionEvent;
import engine.net.server.core.Player;

public interface ConnectionListener
{

	public void clientConnected();
	public void clientConnected(Player player);

	public void clientDisconnected();
	public void clientDisconnected(Player player);

	public void addConnectionEvent(boolean connected);

	public void addConnectionEvent(boolean connected, Player player);
	public void handleConnectionQueue();


}
