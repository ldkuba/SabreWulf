package game.client;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.common_net.PeerList;
import game.Main;
import game.networking.ExampleMessage;

public class ClientMessageListener implements MessageListener
{
	private Main app;
	
	
	
	@Override
	public void receiveMessage(AbstractMessage msg)
	{
		if(msg instanceof PeerList)
		{
			PeerList plm = (PeerList) msg;
			System.out.println(plm.getNoPlayers());
		}
		else
			System.out.println(msg.getClass());

	}
}
