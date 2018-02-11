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
	public void clientConnected(Socket socket){
		System.out.println("Hello");
		Player player = new Player(socket.getInetAddress());
		server.addPlayer(player);
	}

	@Override
	public void clientDisconnected(Socket socket) {

		for(int i = 0; i< server.players.size(); i++){
			if(server.players.get(i).getIp().equals(socket.getInetAddress())){
				server.removePlayer(server.players.get(i));
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
