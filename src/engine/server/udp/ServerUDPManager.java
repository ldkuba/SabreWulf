package engine.server.udp;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import engine.common_net.AbstractMessage;

public class ServerUDPManager extends Thread{
	
	//Listeners add messages to this queue.
	protected static volatile Queue<AbstractMessage> queueMessages = new LinkedList<AbstractMessage>();
	//Gamelogic gives new game states onto this queue.
	protected static volatile Queue<AbstractMessage> queueGameStates = new LinkedList<AbstractMessage>();
	//Current Game State.
	protected static AbstractMessage gameStates = new AbstractMessage();
	
	ServerBroadcasterThreadUDP BSocket;
	ServerListenerThreadUDP SListener;
	
	protected ServerUDPManager() {
		
	}
	
	/*
	public static void main(String args[]) {
		ServerUDPManager UDPManager = new ServerUDPManager();
		UDPManager.run();
	}
	*/
	
	public void run() {
		
		startListener(6666,1024);
		startBroadcastSender("230.0.0.0", 6667);
		
		//GameLogicThreadUDP GLThread = new GameLogicThreadUDP();
		
		//GLThread.start();
	}
	
	private void startListener(int serverPort, int MAX_PACKET_SIZE) {
		SListener = new ServerListenerThreadUDP(serverPort, MAX_PACKET_SIZE);
	}
	
	private void startBroadcastSender(String groupID, int groupPort) {
		BSocket = new ServerBroadcasterThreadUDP(groupID, groupPort);
	}
	
	//Used by the Broadcaster
	public void addToQueueStates(AbstractMessage newState) {
		queueGameStates.add(newState);
	}
	//Game Logic uses these packets to update game.
	public void addToQueueMessages(AbstractMessage msg) {
		queueMessages.add(msg);
	}
	
}
