package engine.net.server;

import engine.net.common_net.AbstractMessage;
import engine.net.common_net.MessageListener;
import engine.net.server.core.Player;
import engine.net.networking_messages.*;

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
		if(msg instanceof LobbyConnectionRequestMessage) {
			LobbyConnectionRequestMessage m = (LobbyConnectionRequestMessage) msg;
			if (gameServer.isFreeGameInstance()) {

				gameInstance = gameServer.getFreeGameInstance();
				source.setName(m.getName());
				gameInstance.addPlayer(source);

				LobbyConnectionResponseMessage lobbyConn = new LobbyConnectionResponseMessage();
				lobbyConn.setAccepted(true);
				lobbyConn.setMessage("Welcome to the gameServer");
				gameServer.sendTCP(lobbyConn, source);

				LobbyUpdateMessage lobbyUpd = new LobbyUpdateMessage();
				lobbyUpd.setPlayersInLobby(gameInstance.getPlayerPayload());
				gameServer.broadcastTCP(lobbyUpd);
			}
			else{
				LobbyConnectionResponseMessage lobbyConn = new LobbyConnectionResponseMessage();
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

