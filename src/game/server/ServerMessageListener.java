package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import game.networking.ExampleMessage;

public class ServerMessageListener implements MessageListener
{/*


	@Override
	public void  (AbstractMessage msg)
	{
		if(msg instanceof ExampleMessage)
		{
			ExampleMessage message = (ExampleMessage) msg;
			int a = message.getA();

			// Do shit with A
		}
	}
*/
	@Override
	public void receiveMessage(AbstractMessage msg) {

	}

}
