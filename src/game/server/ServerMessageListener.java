package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.LobbyQuitMessage;
import engine.server.core.Player;
import engine.server.core.QuitMessage;
import game.networking.*;

import java.io.IOException;

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
		if(msg instanceof LobbyConnectionMessage) {
			LobbyConnectionMessage m = (LobbyConnectionMessage) msg;
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
				server.broadcastTCP(lobbyUpd);
			}
			else{
				LobbyConnectionResponse lobbyConn = new LobbyConnectionResponse();
				lobbyConn.setAccepted(false);
				lobbyConn.setMessage("Server is full");
				server.sendTCP(lobbyConn, source);
			}
		} else if(msg instanceof LockInMessage){
			LockInMessage lim = (LockInMessage) msg;
			source.setReady(true);
			source.setChar(lim.getCharacterSelected());

        } else if(msg instanceof LobbyQuitMessage){

        }

        }
}

