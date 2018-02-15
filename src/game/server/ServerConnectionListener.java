package game.server;

import engine.common_net.ConnectionListener;
import engine.server.core.Player;
import game.networking.QuitMessage;

import java.io.IOException;

public class ServerConnectionListener implements ConnectionListener
{
	private GameServer gameServer;

	ServerConnectionListener(GameServer gameServer){
		this.gameServer = gameServer;
	}

	@Override
	public void clientConnected(Player player){
		System.out.println("Connection");
	}

	@Override
	public void clientDisconnected(Player player) {
		gameServer.players.remove(player);
		player.addMsg(new QuitMessage());
		try {
			player.getSocket().close();
		} catch (IOException e) {
			System.out.println("EXCEPTION PLAYER LEAVING");
		}
	}
}
