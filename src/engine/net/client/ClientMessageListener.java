package engine.net.client;

import engine.net.common_net.AbstractMessage;
import engine.net.common_net.MessageListener;
import engine.net.server.core.Player;
import game.client.Main;
import game.method.SetCurrentGameState;
import engine.net.networking_messages.LobbyUpdateMessage;
import engine.net.networking_messages.PeerCountMessage;
import engine.net.networking_messages.LobbyConnectionResponseMessage;

public class ClientMessageListener implements MessageListener
{
	private Client client;

	public ClientMessageListener(Client client)
	{
		this.client = client;
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player source)
	{
		if(msg instanceof PeerCountMessage)
		{
			PeerCountMessage plm = (PeerCountMessage) msg;
			System.out.println(plm.getNoPlayers());
		}
		else if(msg instanceof LobbyConnectionResponseMessage){
			LobbyConnectionResponseMessage lobbyConn = (LobbyConnectionResponseMessage) msg;
			if(lobbyConn.isAccepted())
			{
				SetCurrentGameState setGameState = new SetCurrentGameState(client.getMain(), Main.lobbyState);
				client.getMain().getMethodExecutor().add(setGameState);
			}else
			{
				System.out.println(lobbyConn.getMessage());
			}
		}

		else if(msg instanceof LobbyUpdateMessage){
		    LobbyUpdateMessage lobbyUpd = (LobbyUpdateMessage) msg;
		    // Here's message containing all the players and their state
        }
		else System.out.println(msg.getClass());
	}
}
