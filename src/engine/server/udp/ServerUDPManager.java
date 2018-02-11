package engine.server.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import engine.common_net.AbstractMessage;
import engine.common_net.udpMessage;


public class ServerUDPManager extends Thread{
	
	
	private boolean defaultSettings = true;
	
	//Listeners add messages to this queue.
	protected static volatile Queue<AbstractMessage> queueMessages = new LinkedList<AbstractMessage>();
	//Gamelogic gives new game states onto this queue.
	protected static volatile Queue<AbstractMessage> queueGameStates = new LinkedList<AbstractMessage>();
	//Current Game State.
	protected static AbstractMessage gameStates = new AbstractMessage();
	
	ServerBroadcasterThreadUDP BSocket;
	ServerListenerThreadUDP SListener;
	
	int portListener;
	int portBroadcast;
	String groupID;
	String serverIP;
	
	private boolean protoLogic = true;
	
	//Default & protoLogic are true;
	protected ServerUDPManager() {
		portListener = 6666;
		portBroadcast = 6667;
		groupID = "230.0.0.0";
		serverIP = "localhost";
	}
	
	//Custom settings;
	protected ServerUDPManager(int portListener, int portBroadcast, String groupID, String serverIP, boolean protoLogic) {
		this.portListener = portListener;
		this.portBroadcast = portBroadcast;
		this.groupID = groupID;
		this.serverIP = serverIP;
		this.protoLogic = protoLogic;
		defaultSettings = false;
	}
	
	//Use Proto-Logic
	protected ServerUDPManager(boolean useProtoLogic) {
		protoLogic = useProtoLogic;
		portListener = 6666;
		portBroadcast = 6667;
		groupID = "230.0.0.0";
		serverIP = "localhost";
	}
	
	public void run() {
		
		if(defaultSettings) {
			System.out.println("Settings: Default");
		} else {
			System.out.println("Settings: Custom");
		}
		
		startListener(portListener,1024);
		startBroadcastSender(groupID, portBroadcast);
		
		showInformation();
		
		if(protoLogic) {
			System.out.println("Starting Proto-Logic");
			protoLogicServer gameLogic = new protoLogicServer();
			gameLogic.run();
		} else {
			System.out.println("Real Game Logic starting...");
		}
	}
	
	private void startListener(int serverPort, int MAX_PACKET_SIZE) {
		SListener = new ServerListenerThreadUDP(serverPort, MAX_PACKET_SIZE);
		SListener.start();
	}
	
	private void startBroadcastSender(String groupID, int groupPort) {
		BSocket = new ServerBroadcasterThreadUDP(groupID, groupPort);
		BSocket.start();
	}
	
	//Used by the Broadcaster
	public void addToQueueStates(AbstractMessage newState) {
		queueGameStates.add(newState);
	}
	//Game Logic uses these packets to update game.
	public void addToQueueMessages(AbstractMessage msg) {
		queueMessages.add(msg);
	}
	
	public void showInformation() {
		System.out.println();
		try {
		System.out.println("Server IP: " + InetAddress.getByName(serverIP));
		} catch (IOException e){
			e.printStackTrace();
		}
		System.out.println("Port Listener: " + portListener);
		System.out.println("Port Broadcaster: " + portBroadcast);
		System.out.println("Group Address/ID: " + groupID);
	}
	
	public void closeUDPManager() {
		BSocket.closeMulticast();
		SListener.closeUDPListener();
		return;
	}
	
}
