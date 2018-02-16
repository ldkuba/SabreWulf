package game.client;

import engine.net.client.Client;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.*;
import engine.net.server.core.Player;

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
		this.client = client;
		soundManager = new SoundManager();
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player player) {

	}

	@Override
	public void receiveMessage(AbstractMessage msg)
	{
		if(msg instanceof PeerCountMessage)
		String soundName = "message_beep";
		if(msg instanceof PeerList)
		{
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			invokeSound(soundName);
			PeerList plm = (PeerList) msg;
			System.out.println(plm.getNoPlayers());
			soundManager.pauseSoundSource(soundName);
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
		else System.out.println(msg.getClass());
	}

	@Override
	public void addMessage(AbstractMessage message, Player player) {

	}



	private void invokeSound(String soundName){
		this.soundManager.init();
		this.soundManager.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		Sound.setupSounds(soundManager, "res/sounds/beep.ogg", soundName);
	}
}
