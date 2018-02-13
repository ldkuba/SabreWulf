package game.client;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.Main;
import game.method.SetCurrentGameState;
import game.networking.PeerList;
import game.networking.ServerConnectionReplyMessage;

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
		else if(msg instanceof ServerConnectionReplyMessage){
			ServerConnectionReplyMessage scrm = (ServerConnectionReplyMessage) msg;
			if(scrm.isAccepted())
			{
				scrm.getPlayersInLobby();
				SetCurrentGameState setGameState = new SetCurrentGameState(client.getMain(), Main.lobbyState);
				client.getMain().getMethodExecutor().add(setGameState);
			}else
			{
				System.out.println(scrm.getMessage());
			}
		}
		else System.out.println(msg.getClass());
	}
}
