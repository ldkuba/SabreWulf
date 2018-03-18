package game.client;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.BattleBeginMessage;
import engine.net.common_net.networking_messages.EntityUpdateMessage;
import engine.net.common_net.networking_messages.ExecuteActionMessage;
import engine.net.common_net.networking_messages.LobbyConnectionResponseMessage;
import engine.net.common_net.networking_messages.LobbyUpdateMessage;
import engine.net.common_net.networking_messages.PeerCountMessage;
import engine.net.common_net.networking_messages.TimerEventMessage;
import engine.net.server.core.NetPlayer;
import game.common.config;

public class ClientTCPMessageListener implements MessageListener
{
	private Main app;
	private BlockingQueue<AbstractMessage> abstractMessageInbound;

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
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			//app.getSoundManager().invokeSound("beep", false);
			PeerCountMessage plm = (PeerCountMessage) msg;
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
		    	
		    ArrayList<NetPlayer> updatedPlayers = new ArrayList<>();
		    
		    for(int i = 0; i < lobbyUpd.getPlayersInLobby().size(); i++)
		    {
		    	NetPlayer netPlayer = new NetPlayer(null);
		    	netPlayer.setChar(lobbyUpd.getPlayersInLobby().get(i).getCharacterSelection());
		    	netPlayer.setCurrentGame(lobbyUpd.getPlayersInLobby().get(i).getCurrentGame());
		    	netPlayer.setName(lobbyUpd.getPlayersInLobby().get(i).getName());
		    	netPlayer.setPlayerId(lobbyUpd.getPlayersInLobby().get(i).getNetPlayerId());
		    	netPlayer.setReady(lobbyUpd.getPlayersInLobby().get(i).getReady());
		    	
		    	updatedPlayers.add(netPlayer);
		    }
		    
		    app.getNetworkManager().setPlayers(updatedPlayers);
		    
		    for(int i=0; i<config.gameConnectionLimit; i++){
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
			if(!app.getSoundManager().getIsMuted()){
				if (time.getTimePayload() == 0){
					app.getSoundManager().invokeSound("countEnd", false, true);
				} else {
					app.getSoundManager().invokeSound("count", false, true);
				}
			}
			System.out.println("Countdown: " + time.getTimePayload());
		}

		else if(msg instanceof BattleBeginMessage){
			if(!app.getSoundManager().getIsMuted()){
				app.getSoundManager().stopSoundSource("background/lobby");
			}			
			//Create and setup player manager
			
			app.getStateManager().setCurrentState(Main.gameState);
		}else if(msg instanceof EntityUpdateMessage)
		{
			EntityUpdateMessage eum = (EntityUpdateMessage) msg;
			
			app.getNetworkManager().updateEntityInNetworkManager(eum.getEntity());
		}else if(msg instanceof ExecuteActionMessage)
		{
			ExecuteActionMessage eam = (ExecuteActionMessage) msg;
			eam.getAction().executeClient();
		}
	}

	@Override
	public void addMessage(AbstractMessage message, NetPlayer player) {

	}
}
