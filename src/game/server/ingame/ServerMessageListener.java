package game.server.ingame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.entity.component.NetTransformComponent;
import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.AttackPlayerMessage;
import engine.net.common_net.networking_messages.DesiredLocationMessage;
import engine.net.common_net.networking_messages.PeerCountMessage;
import engine.net.server.core.NetPlayer;
import game.common.actors.Player;

public class ServerMessageListener implements MessageListener
{
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

	private boolean debug = true;

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
			app.getSoundManager().invokeSound(soundName, false);
			PeerCountMessage plm = (PeerCountMessage) msg;
			System.out.println(plm.getNoPlayers());
			app.getSoundManager().pauseSoundSource(soundName);
		}else if(msg instanceof DesiredLocationMessage)
		{
			DesiredLocationMessage dlm = (DesiredLocationMessage) msg;
			
			ServerMain.gameState.getPlayerManager().getPlayer(player.getPlayerId()).calculatePath(dlm.getPos(), ServerMain.gameState.getMap().getNavmesh());

			/*
			 * Testing Zone
			 */

			System.out.println("Recieved path message in game");
		} else if(msg instanceof AttackPlayerMessage) {


			AttackPlayerMessage apm = (AttackPlayerMessage) msg;

			int playerDamaged = apm.getPlayerID();
			float damageToGive;

			//Get bully and victim.
			Player attacker = ServerMain.gameState.getPlayerManager().getPlayer(player.getPlayerId());
			Player playerAttacked = ServerMain.gameState.getPlayerManager().getPlayer(playerDamaged);

			/*
			Check if they are in the same team.
			 */




			if (debug) {
				System.out.println(attacker.getName() + " attacked " + playerAttacked.getName());
			}

			if(debug) { System.out.println("Custom attack Set"); }

			float damageTest = 20.0f;

			playerAttacked.lostHealth(damageTest);
		}
	}

	@Override
	public void addMessage(AbstractMessage message, NetPlayer player)
	{
		abstractMessageInbound.add(new MessageEvent(message, player));
	}
}
