package engine.server.udp;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import engine.common_net.AbstractMessage;
import game.networking.ExampleMessage;

public class ServerUDPManager extends Thread{
	
	//Listeners add messages to this queue.
	protected static volatile Queue<AbstractMessage> queueMessages = new LinkedList<AbstractMessage>();
	//Gamelogic gives new game states onto this queue.
	protected static volatile Queue<byte[]> queueGameStates = new LinkedList<byte[]>();
	//Current Game State.
	protected static AbstractMessage gameStates = new AbstractMessage();
	
	protected ServerUDPManager() {
		
	}
	
	public static void main(String args[]) {
		ServerUDPManager UDPManager = new ServerUDPManager();
		UDPManager.run();
	}
	
	public void run() {
		ClientBroadcasterThreadUDP BSocket = new ClientBroadcasterThreadUDP("230.0.0.0", 6667);
		ClientListenerThreadUDP SLThread = new ClientListenerThreadUDP(6666,1024);
		
		//GameLogicThreadUDP GLThread = new GameLogicThreadUDP();
		
		SLThread.start();
		BSocket.start();
		
		//GLThread.start();
	}
	
	public void addToQueueStates(byte[] newState) {
		queueGameStates.add(newState);
	}
	
	public void addToQueueMessages(AbstractMessage msg) {
		queueMessages.add(msg);
	}
	
}
