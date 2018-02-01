package engine.client.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/*
 * groupID -> 230.0.0.0
 * groupPort    -> Multicast Port
 */

public class ClientBroadcastReceiverUDP extends Thread{

	private static MulticastSocket BCSocket;	//(BROADCAST_CLIENT_SOCKET)
	private int groupPort;
	private String group;
	private static DatagramPacket gamePacket;
	
	public ClientBroadcastReceiverUDP(int groupPort, String group) {
		this.groupPort = groupPort;
		this.group = group;
	}
	
	public void run() {
		try {
			BCSocket = new MulticastSocket(groupPort);
			InetAddress groupID = InetAddress.getByName(group);
			BCSocket.joinGroup(groupID);
		} catch (IOException e) {
			System.err.println("Unable to connect to Broadcast port.");
		}
		while(true) {
			try {
				BCSocket.receive(gamePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Received Packet");
		}
	}
	
	public DatagramPacket getNewStatePacket(DatagramPacket packet) {
		return gamePacket;
	}
	
	public void closeBCSocket() {
		BCSocket.close();
	}
	
}
