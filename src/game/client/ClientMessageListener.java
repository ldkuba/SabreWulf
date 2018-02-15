package engine.net.client.core;

import engine.net.client.Client;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;
import engine.net.common_net.networking_messages.LobbyUpdateMessage;
import engine.net.common_net.networking_messages.PeerCountMessage;
import engine.net.common_net.networking_messages.LobbyConnectionResponseMessage;

public class ClientMessageListener
{
	private Client client;

	public ClientMessageListener(Client client)
	{
		this.client = client;
	}

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
