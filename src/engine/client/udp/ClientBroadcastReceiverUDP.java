package engine.client.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class ClientBroadcastReceiverUDP extends Thread{

	private static MulticastSocket BCSocket;	//(BROADCAST_CLIENT_SOCKET)
	private int groupPort;
	private InetAddress groupID;
	private static DatagramPacket gamePacket;
	
	public ClientBroadcastReceiverUDP(int port, InetAddress groupID) {
		groupPort = port;
		this.groupID = groupID;
	}
	
	public void run() {
		try {
			BCSocket = new MulticastSocket(groupPort);
			BCSocket.joinGroup(groupID);
		} catch (IOException e) {
			System.err.println("Unable to connect to Broadcast port.");
		}
		while(true) {
			BCSocket.receive(gamePacket);
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
