package game.server;

import engine.common_net.ConnectionListener;
import engine.server.core.Player;

import java.net.Socket;

public class ServerConnectionListener implements ConnectionListener
{
	private Server server;

	ServerConnectionListener(Server server){
		this.server=server;
	}

	@Override
	public void clientConnected(Socket socket){
		Player player = new Player(socket.getInetAddress());
		server.addPlayer(player);

	}

	@Override
	public void clientDisconnected(Socket socket) {

	}
}
