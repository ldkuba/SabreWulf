package engine.server.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import engine.common_net.AbstractMessage;
import engine.common_net.Serialization;


/*
 * Not recommended to run as a thread.
 * Initializes Multicast Socket to send packets to all clients.
 * 
 * SEND PACKETS TO CLIENTS -> multicast
 * 		Sends the serialized message to the clients.
 * 
 * groupID -> 230.0.0.0
 * 
 */

public class ServerBroadcasterThreadUDP extends Thread{
	
	private static DatagramSocket BCSocket;
	
	private InetAddress clientIP;
	
	private int portBroadcaster;
	
	protected static ServerUDPManager ServerQueue = new ServerUDPManager();
	private Serialization NetTools = new Serialization();
	
	ServerBroadcasterThreadUDP(int portBroadcaster) {
		this.portBroadcaster = portBroadcaster;
	}
	
	
    public void run(){

    	startBroadcaster();
    	
    	//Send updates to all players.
    	while (true) {
    		if(!/*Queue to get messages*/.isEmpty()) {
    			AbstractMessage gameState = ServerUDPManager.queueGameStates.poll();
    			byte[] gameStateByte = NetTools.serialize(gameState);
    			broadcast(gameStateByte);
    			System.out.println("Broadcast sent");
    		}
    	}
    }
    
    public void startBroadcaster() {
    	try {
    		portBroadcaster = ServerQueue.getBroadcastPort();
			BCSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void broadcast(byte[] buffer) {
    	byte[] gameBuffer = buffer;
    	for(int i = 0; i < ServerUDPManager.Players.size(); i++) {
    		InetAddress clientIP = ServerUDPManager.Players.get(i).getSocket().getInetAddress();
    		DatagramPacket multiPacket = new DatagramPacket(gameBuffer, gameBuffer.length, clientIP, portBroadcaster);
    		try {
				BCSocket.send(multiPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void closeBroadcast() {
    	BCSocket.close();
    }

}
