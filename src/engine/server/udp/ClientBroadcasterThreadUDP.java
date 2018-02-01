package engine.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

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

public class ClientBroadcasterThreadUDP extends Thread {
	
	private static MulticastSocket MCSocket;
	private String groupID;
	private static InetAddress group;
	private int groupPort;
	
	
	ClientBroadcasterThreadUDP(String groupID, int groupPort) {
		this.groupID = groupID;
		this.groupPort = groupPort;
	}
	
    public void run(){
    	
    	try {
			MCSocket = new MulticastSocket(groupPort);
			group = InetAddress.getByName(groupID);
			MCSocket.joinGroup(group);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    //Send packet to all clients connected to the group.
    public void multicast(byte[] buffer) {
    	DatagramPacket multiPacket = new DatagramPacket(buffer, buffer.length, group, groupPort);
    	try {
			MCSocket.send(multiPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void closeMulticast() {
    	MCSocket.close();
    }

}
