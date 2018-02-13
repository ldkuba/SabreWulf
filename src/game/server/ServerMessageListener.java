package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.networking.ConnectionMessage;
import game.networking.ServerConnectionReplyMessage;

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
				System.out.println(m.getName());
				gameInstance.addPlayer(source);

				ServerConnectionReplyMessage scrm = new ServerConnectionReplyMessage();
				scrm.setAccepted(true);
				scrm.setPlayersInLobby(gameInstance.getPlayers());
				scrm.setMessage("Welcome to the server");
				server.sendTCP(scrm, source);
			}
			else{
				ServerConnectionReplyMessage scrm = new ServerConnectionReplyMessage();
				scrm.setAccepted(false);
				scrm.setMessage("Server is full");
				server.sendTCP(scrm, source);
			}
		}

	}
}
