package game.client;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.*;
import engine.net.server.core.Player;
import engine.sound.Sound;
import engine.sound.SoundManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientMessageListener implements MessageListener
{
	private Main app;
	private BlockingQueue<AbstractMessage> abstractMessageInbound;

	private final int maxTraffic = 100;

	public ClientMessageListener(Main app)
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
			String soundName = "beep";
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			app.getSoundManager().invokeSound(soundName);
			PeerCountMessage plm = (PeerCountMessage) msg;
			//System.out.println(plm.getNoPlayers());
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
		   	System.out.println( ((LobbyUpdateMessage) msg).getPlayersInLobby().get(0).getName());
		    for(int i=0; i<lobbyUpd.getPlayersInLobby().size(); i++){
		    	Main.lobbyState.updatePlayer(i, lobbyUpd.getPlayersInLobby().get(i).getCharacterSelection());
				System.out.println(lobbyUpd.getTest());
		    }
        }

        else if(msg instanceof TimerEventMessage){
			TimerEventMessage time = (TimerEventMessage) msg;
			System.out.println("Countdown: " + time.getTimePayload());
		}

		else if(msg instanceof BattleBeginMessage){
			app.getStateManager().setCurrentState(Main.gameState);
		}
		System.out.println(msg.getClass());
	}

	@Override
	public void addMessage(AbstractMessage message, Player player) {

	}
}
