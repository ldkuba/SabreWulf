package game.server.ingame;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.BattleBeginMessage;
import engine.net.common_net.networking_messages.LobbyConnectionResponseMessage;
import engine.net.common_net.networking_messages.LobbyUpdateMessage;
import engine.net.common_net.networking_messages.PeerCountMessage;
import engine.net.common_net.networking_messages.TimerEventMessage;
import engine.net.server.core.Player;
import game.client.Main;
import game.server.states.ServerMain;

public class ServerMessageListener implements MessageListener
{
	private ServerMain app;
	private BlockingQueue<AbstractMessage> abstractMessageInbound;

	private final int maxTraffic = 100;

	public ServerMessageListener(ServerMain app)
	{
		this.app = app;
		abstractMessageInbound = new LinkedBlockingQueue<>();
	}
	@Override
	public void addMessage(AbstractMessage message){
		abstractMessageInbound.add(message);
	}

	public void handleMessageQueue(){
		int count = 0;
		while(count < maxTraffic && !abstractMessageInbound.isEmpty()) {
			try {
				receiveMessage(abstractMessageInbound.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player player) {

	}

	@Override
	public void receiveMessage(AbstractMessage msg)
	{
		if(msg instanceof PeerCountMessage)
		{
			String soundName = "message_beep";
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			app.getSoundManager().invokeSound(soundName, false);
			PeerCountMessage plm = (PeerCountMessage) msg;
			System.out.println(plm.getNoPlayers());
			app.getSoundManager().pauseSoundSource(soundName);
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

        else if(msg instanceof TimerEventMessage){
			TimerEventMessage time = (TimerEventMessage) msg;
			System.out.println("Countdown: " + time.getTimePayload());
		}

		else if(msg instanceof BattleBeginMessage){
			app.getStateManager().setCurrentState(Main.gameState);
		}
		else System.out.println(msg.getClass());
	}

	@Override
	public void addMessage(AbstractMessage message, Player player) {

	}
}
