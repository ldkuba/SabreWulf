package engine.server.udp;

import engine.common_net.AbstractMessage;
import engine.common_net.udpMessage;

public class protoLogicServer extends Thread{

	private udpMessage gameMessage;
	
	public static enum MessageType {ATTACK, MOVEMENT}
	private static ServerUDPManager udpManager = new ServerUDPManager();
	
	public protoLogicServer() {
		
	}
	
	public void run() {
		ServerUDPManager udpManager = new ServerUDPManager();
		
		while(true) {
			if(!udpManager.queueMessages.isEmpty()) {
				AbstractMessage newMessage = udpManager.queueMessages.poll();
				if(newMessage.getClass().isInstance(gameMessage)) {
					gameMessage = (udpMessage) newMessage;
					gameLogic(gameMessage);
				}
			}
		}
	}
	
	private void gameLogic(udpMessage msg) {
		String type = msg.getType();
		if(type.equals("MOVEMENT")) {
			msg.setType("YOU MOVED");
			udpManager.addToQueueStates(msg);
		} else {
			System.out.println("Unable to process message");
		}
		
	}
	
}
