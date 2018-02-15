package game.client;

import engine.net.client.Client;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;
import engine.net.common_net.networking_messages.LobbyUpdateMessage;
import engine.net.common_net.networking_messages.PeerCountMessage;
import engine.net.common_net.networking_messages.LobbyConnectionResponseMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientMessageListener implements MessageListener
{
	private Main app;
	private CopyOnWriteArrayList<AbstractMessage> abstractMessageInbound;

	public ClientMessageListener(Main client)
	{
		this.app = client;
		abstractMessageInbound = new CopyOnWriteArrayList<>();
	}
	@Override
	public void addMessage(AbstractMessage message){
		abstractMessageInbound.add(message);
	}

	public void handleMessageQueue(){
		for(AbstractMessage msg : abstractMessageInbound){
			receiveMessage(msg);
		}
		abstractMessageInbound.clear();
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player player) {

	}

	@Override
	public void receiveMessage(AbstractMessage msg)
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
				app.getStateManager().setCurrentState(Main.lobbyState);
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

	@Override
	public void addMessage(AbstractMessage message, Player player) {

	}


}
