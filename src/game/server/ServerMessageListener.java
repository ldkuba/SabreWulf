package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.networking.ConnectionMessage;
import game.networking.LobbyConnectionResponse;
import game.networking.LobbyUpdateMessage;

public class ServerMessageListener implements MessageListener
{
	private Server server;
	private GameInstance gameInstance;
	ServerMessageListener(Server server){
		this.server=server;
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player source) {

		// Handling Play button action here
		if(msg instanceof ConnectionMessage) {
			ConnectionMessage m = (ConnectionMessage) msg;
			if (server.isFreeGameInstance()) {

				gameInstance = server.getFreeGameInstance();
				source.setName(m.getName());
				gameInstance.addPlayer(source);

				LobbyConnectionResponse lobbyConn = new LobbyConnectionResponse();
				lobbyConn.setAccepted(true);
				lobbyConn.setMessage("Welcome to the server");
				server.sendTCP(lobbyConn, source);

				LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
				lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
				server.sendTCP(lobbyUpd, source);
			}
			else{
				LobbyConnectionResponse lobbyConn = new LobbyConnectionResponse();
				lobbyConn.setAccepted(false);
				lobbyConn.setMessage("Server is full");
				server.sendTCP(lobbyConn, source);
			}
		}

	}
}
