package game.server.ingame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.DesiredLocationMessage;
import engine.net.common_net.networking_messages.PeerCountMessage;
import engine.net.server.core.NetPlayer;
import game.server.states.ServerMain;

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
			
			//Compute the path
			
			ServerMain.gameState.getPlayerManager().getPlayer(player.getPlayerId()).setTargetLocation(dlm.getPos());
		}	
		
		System.out.println("Recieved message in game");
	}

	@Override
	public void addMessage(AbstractMessage message, NetPlayer player)
	{
		abstractMessageInbound.add(new MessageEvent(message, player));
	}
}
