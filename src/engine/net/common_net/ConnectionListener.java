package engine.net.common_net;

import engine.net.common_net.networking_messages.ConnectionEvent;
import engine.net.server.core.NetPlayer;

public interface ConnectionListener
{

	public void clientConnected();
	public void clientConnected(NetPlayer player);

	public void clientDisconnected();
	public void clientDisconnected(NetPlayer player);

	public void addConnectionEvent(boolean connected);

	public void addConnectionEvent(boolean connected, NetPlayer player);
	public void handleConnectionQueue();


}
