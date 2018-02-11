package engine.common_net;

import java.net.Socket;

public interface ConnectionListener
{
	public void clientConnected(Socket socket);
	
	public void clientDisconnected(Socket socket);
}
