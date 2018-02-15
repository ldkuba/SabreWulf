package engine.server.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;
import game.networking.NetworkLogic;


public class ServerUDPManager extends Thread{
	
	
	private boolean defaultSettings = true;
	private boolean protoLogic = true;
	
	//Player list.
	protected static volatile LinkedList<Player> Players = new LinkedList<Player>();
	//Listeners add messages to this queue.
	protected static volatile Queue<ClientInformation> queueMessages = new LinkedList<ClientInformation>();
	//Gamelogic gives new game states onto this queue.
	protected static volatile Queue<AbstractMessage> queueGameStates = new LinkedList<AbstractMessage>();
	//Current Game State.
	protected static AbstractMessage gameStates = new AbstractMessage();
	
	private NetworkLogic gameLogic = new NetworkLogic();
	
	private boolean inGame = false;
	
	ServerBroadcasterThreadUDP BSocket;
	ServerListenerThreadUDP SListener;
	
	int portListener;
	int portBroadcast;
	String groupID;
	String serverIP;
	
	//Default settings & protoLogic are true;
	public ServerUDPManager() {
		portListener = 6666;
		portBroadcast = 6667;
		groupID = "230.0.0.0";
		serverIP = "localhost";
		inGame = true;
	}
	
	//Custom settings;
	public ServerUDPManager(int portListener, int portBroadcast, String groupID, String serverIP, boolean protoLogic) {
		this.portListener = portListener;
		this.portBroadcast = portBroadcast;
		this.groupID = groupID;
		this.serverIP = serverIP;
		this.protoLogic = protoLogic;
		defaultSettings = false;
		inGame = true;
	}
	
	//Use Proto-Logic
	public ServerUDPManager(boolean useProtoLogic) {
		protoLogic = useProtoLogic;
		portListener = 6666;
		portBroadcast = 6667;
		groupID = "230.0.0.0";
		serverIP = "localhost";
		inGame = true;
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
		} else {
			System.out.println("Real Game Logic starting...");
		}
		
		//Update players position.
		while(inGame) {
			if(!queueMessages.isEmpty()) {
				ClientInformation info = queueMessages.poll();
				updatePlayer(info.getIP(), info.getMsg());
			}
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
	public void addToQueueMessages(ClientInformation msg) {
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
	
	public void updatePlayer(InetAddress playerIP, AbstractMessage data) {
		for(int i = 0; i < Players.size(); i++) {
			if(Players.get(i).getIp().equals(playerIP)) {
				
				AbstractMessage updateMessage = gameLogic.process(data);
				if(updateMessage.equals(null)) {
					System.err.println("Wrong packet received.");
				} else {
					//Correct packet. Player updated.
					addToQueueStates(updateMessage);
					System.out.println("Player updated: UDP Manager");
				}
				break;
			}
		}
	}
	
	public void addPlayers(Player newPlayer) {
		Players.add(newPlayer);
	}
	
	public void removePlayer(Player newPlayer) {
		Players.remove(newPlayer);
	}
	//Add all players to the game.
	public void setPlayers(LinkedList<Player> players) {
		Players = players;
	}
	
	public void emptyUDPServer() {
		Players = null;
	}
	
	public void closeUDPManager() {
		BSocket.closeMulticast();
		SListener.closeUDPListener();
		inGame = false;
	}
	
}
