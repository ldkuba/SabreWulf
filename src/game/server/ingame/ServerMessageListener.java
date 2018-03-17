package game.server.ingame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.*;
import engine.net.server.core.NetPlayer;
import game.common.actors.Player;

public class ServerMessageListener implements MessageListener
{

	private boolean debug = true;

	private class MessageEvent
	{
		public AbstractMessage msg;
		public NetPlayer player;
		
		public MessageEvent(AbstractMessage msg, NetPlayer player)
		{
			this.msg = msg;
			this.player = player;
		}
	};
	
	private ServerMain app;
	private BlockingQueue<MessageEvent> abstractMessageInbound;

	private final int maxTraffic = 100;

	public ServerMessageListener(ServerMain app)
	{
		this.app = app;
		abstractMessageInbound = new LinkedBlockingQueue<>();
	}

	@Override
	public void addMessage(AbstractMessage message)
	{
	}

	public void handleMessageQueue()
	{
		int count = 0;
		while (count < maxTraffic && !abstractMessageInbound.isEmpty())
		{
			try
			{
				MessageEvent msgEvent = abstractMessageInbound.take();
				receiveMessage(msgEvent.msg, msgEvent.player);
			}catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			count++;
		}
	}

	@Override
	public void receiveMessage(AbstractMessage msg)
	{

	}

	@Override
	public void receiveMessage(AbstractMessage msg, NetPlayer player)
	{
		if(msg instanceof PeerCountMessage)
		{
			String soundName = "message_beep";
			PeerCountMessage pcm = (PeerCountMessage) msg;
			System.out.println("Number of players online: " + pcm.getNoPlayers());
			app.getSoundManager().invokeSound(soundName, false, true);
			PeerCountMessage plm = (PeerCountMessage) msg;
			System.out.println(plm.getNoPlayers());
			app.getSoundManager().pauseSoundSource(soundName);
		}else if(msg instanceof DesiredLocationMessage)
		{
			DesiredLocationMessage dlm = (DesiredLocationMessage) msg;
			
			ServerMain.gameState.getPlayerManager().getPlayer(player.getPlayerId()).calculatePath(dlm.getPos(), ServerMain.gameState.getMap().getNavmesh());
			
			System.out.println("Recieved path message in game");
		} else if(msg instanceof CoordinateMessage) {

			System.out.println("Received Coordinate MEssage");

			CoordinateMessage entityPos = (CoordinateMessage) msg;

			Vec3 coordinates = entityPos.getCoordinates();
			float damageToGive;

			Player attacker = ServerMain.gameState.getPlayerManager().getPlayer(player.getPlayerId());
			//Check if entity is a player
			boolean clickedPlayer = false;
			for(int i = 0; i < ServerMain.gameState.getPlayerManager().getPlayers().size(); i++) {
				Player currentPlayer = ServerMain.gameState.getPlayerManager().getPlayer(i);

				NetTransformComponent currTransform = (NetTransformComponent) currentPlayer.getEntity().getComponent(NetTransformComponent.class);

				//Check in range
				if ( Vec3.distance(currTransform.getPosition(),coordinates) <=1.0f && Vec3.distance(currTransform.getPosition(),coordinates) >= -1.0f){

					//Make sure you do not attack yourself.
					if(currentPlayer.equals(attacker)) {
						System.out.println("Not myself!");
					}else {
						currentPlayer.lostHealth(attacker.getDamage());
						clickedPlayer = true;
					}
				}
			}

			if(!clickedPlayer) {
				//More Search
			}

		}

	}

	@Override
	public void addMessage(AbstractMessage message, NetPlayer player)
	{
		abstractMessageInbound.add(new MessageEvent(message, player));
	}
}
