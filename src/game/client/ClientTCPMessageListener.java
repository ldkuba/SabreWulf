package game.client;
import engine.net.client.udp.ClientReceiverUDP;
import engine.net.common_net.MessageListener;
import engine.net.common_net.UDPTools;
import engine.net.common_net.networking_messages.*;
import engine.net.server.core.NetPlayer;
import engine.sound.Sound;
import engine.sound.SoundManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientTCPMessageListener implements MessageListener
{
	private Main app;
	private BlockingQueue<AbstractMessage> abstractMessageInbound;
	private ClientReceiverUDP UDPin;
	private final int maxTraffic = 100;

	public ClientTCPMessageListener(Main app)
	{
		this.app = app;
		abstractMessageInbound = new LinkedBlockingQueue<>();
	}
	@Override
	public void addMessage(AbstractMessage message){
		abstractMessageInbound.add(message);
	}

	@Override
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
	public void receiveMessage(AbstractMessage msg, NetPlayer player) {

	}

	@Override
	public void receiveMessage(AbstractMessage msg)
	{
		if(msg instanceof PeerCountMessage)
		{
			String soundName = "beep";
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			app.getSoundManager().invokeSound(soundName, false);
			PeerCountMessage plm = (PeerCountMessage) msg;
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

		    for(int i=0; i<6; i++){
		    	if(i<lobbyUpd.getPlayersInLobby().size()){
		    		Main.lobbyState.updatePlayer(i, lobbyUpd.getPlayersInLobby().get(i).getCharacterSelection(), lobbyUpd.getPlayersInLobby().get(i).getName());
		    	}
		    	else{
		    		Main.lobbyState.updatePlayer(i, -1, "");
		    	}
			}
        }

        else if(msg instanceof TimerEventMessage){
			TimerEventMessage time = (TimerEventMessage) msg;
			System.out.println("Countdown: " + time.getTimePayload());
		}

		else if(msg instanceof BattleBeginMessage){
			app.getSoundManager().stopSoundSource("background/lobby");
			app.getNetworkManager().startUDPReceiver();
			
			//Create and setup player manager
			
			app.getStateManager().setCurrentState(Main.gameState);
		}
	}

	@Override
	public void addMessage(AbstractMessage message, NetPlayer player) {

	}
}
