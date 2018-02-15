package game.client;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.Main;
import game.method.SetCurrentGameState;
import game.networking.LobbyUpdateMessage;
import game.networking.PeerList;
import game.networking.LobbyConnectionResponse;

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
		if(msg instanceof PeerList)
		{
			PeerList plm = (PeerList) msg;
			System.out.println(plm.getNoPlayers());
		}
		else if(msg instanceof LobbyConnectionResponse){
			LobbyConnectionResponse lobbyConn = (LobbyConnectionResponse) msg;
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
