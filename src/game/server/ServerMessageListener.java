package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import game.networking.ExampleMessage;

public class ServerMessageListener implements MessageListener
{
	private Server server;
	ServerMessageListener(Server server){
		this.server=server;
	}


	@Override
	public void receiveMessage(AbstractMessage msg) {

	}

}
