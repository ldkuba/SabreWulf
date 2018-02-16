package game.client;

import org.lwjgl.openal.AL11;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import engine.sound.Sound;
import engine.sound.SoundManager;
import game.Main;
import game.method.SetCurrentGameState;
import game.networking.LobbyUpdateMessage;
import game.networking.PeerList;
import game.networking.LobbyConnectionResponse;

public class ClientMessageListener implements MessageListener
{
	private Client client;
	private SoundManager soundManager;

	public ClientMessageListener(Client client)
	{
		this.client = client;
		soundManager = new SoundManager();
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player source)
	{
		String soundName = "message_beep";
		if(msg instanceof PeerList)
		{
			invokeSound(soundName);
			PeerList plm = (PeerList) msg;
			System.out.println(plm.getNoPlayers());
			soundManager.pauseSoundSource(soundName);
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
	
	private void invokeSound(String soundName){
		this.soundManager.init();
		this.soundManager.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
		Sound.setupSounds(soundManager, "res/sounds/beep.ogg", soundName);
	}
}
