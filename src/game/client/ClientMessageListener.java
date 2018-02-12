package game.client;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.Main;
import game.method.SetCurrentGameState;
import game.networking.NewLobbyPlayerMessage;
import game.networking.PeerList;
import game.networking.ServerConnectionReplyMessage;
import game.networking.UpdateLobbyPlayerMessage;

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
		}else if(msg instanceof ServerConnectionReplyMessage)
		{
			ServerConnectionReplyMessage scrm = (ServerConnectionReplyMessage) msg;
			if(scrm.isAccepted())
			{
				Main.lobbyState.setLocalPlayerIndex(scrm.getSlot());
				SetCurrentGameState setGameState = new SetCurrentGameState(client.getMain(), Main.lobbyState);
				client.getMain().getMethodExecutor().add(setGameState);
			}else
			{
				System.out.println(scrm.getMessage());
			}
		}else if(msg instanceof NewLobbyPlayerMessage)
		{
			// todo display name when font rendering is done and that a player
			// connected
			NewLobbyPlayerMessage nlpm = (NewLobbyPlayerMessage) msg;
		}else if(msg instanceof UpdateLobbyPlayerMessage)
		{
			UpdateLobbyPlayerMessage ulpm = (UpdateLobbyPlayerMessage) msg;
			Main.lobbyState.updatePlayer(ulpm.getSlot(), ulpm.getSelection());
		}

		System.out.println(msg.getClass());
	}
}
