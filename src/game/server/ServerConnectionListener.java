package game.server;

import engine.common_net.ConnectionListener;
import engine.server.core.Player;

import java.io.IOException;
import java.net.Socket;

public class ServerConnectionListener implements ConnectionListener
{
	private Server server;

	ServerConnectionListener(Server server){
		this.server=server;
	}

	@Override
	public void clientConnected(Player player){
		System.out.println("Connection");
	}

	@Override
	public void clientDisconnected(Player player) {
		server.players.remove(player);
		try {
			player.getSocket().close();
			server.removePlayer(player);
		} catch (IOException e) {
			System.out.println("EXCEPTION PLAYER LEAVING");
		}
	}
}
