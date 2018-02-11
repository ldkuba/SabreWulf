package game.networking;

import engine.common_net.AbstractMessage;

/*
 * Process all kinds of messages
 * Messages:
 * 		ExampleMessage
 */

public class NetworkLogic {
	
	public NetworkLogic() {
	}
	
	public AbstractMessage process(AbstractMessage msg) {
		if(msg instanceof ExampleMessage) {
			System.out.println("Example Message received.");
			//DO updates
			ExampleMessage message = (ExampleMessage) msg;
			return message;
		} else {
			System.out.println("Wrong packet.");
		}
		return null;
	}	
}