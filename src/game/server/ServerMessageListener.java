package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.networking.*;

public class ServerMessageListener implements MessageListener
{
	private GameServer gameServer;
	private GameInstance gameInstance;
	ServerMessageListener(GameServer gameServer){
		this.gameServer = gameServer;
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player source) {

		// Handling Play button action here
		if(msg instanceof LobbyConnectionMessage) {
			LobbyConnectionMessage m = (LobbyConnectionMessage) msg;
			if (gameServer.isFreeGameInstance()) {

				gameInstance = gameServer.getFreeGameInstance();
				source.setName(m.getName());
				gameInstance.addPlayer(source);

				LobbyConnectionResponse lobbyConn = new LobbyConnectionResponse();
				lobbyConn.setAccepted(true);
				lobbyConn.setMessage("Welcome to the gameServer");
				gameServer.sendTCP(lobbyConn, source);

				LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
				lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
				gameServer.broadcastTCP(lobbyUpd);
			}
			else{
				LobbyConnectionResponse lobbyConn = new LobbyConnectionResponse();
				lobbyConn.setAccepted(false);
				lobbyConn.setMessage("GameServer is full");
				gameServer.sendTCP(lobbyConn, source);
			}
		} else if(msg instanceof LockInMessage){
			LockInMessage lim = (LockInMessage) msg;
			source.setReady(true);
			source.setChar(lim.getCharacterSelected());

        } else if(msg instanceof LobbyQuitMessage){

        }

        }
}

