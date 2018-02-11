package engine.client.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import engine.common_net.AbstractMessage;
import engine.common_net.Deserialization;
import engine.common_net.udpMessage;

/*
 * groupID -> 230.0.0.0
 * groupPort    -> Multicast Port
 */

public class ClientBroadcastReceiverUDP extends Thread{

	private static MulticastSocket BCSocket;	//(BROADCAST_CLIENT_SOCKET)
	private int groupPort;
	private String group;
	private InetAddress groupID;
	private DatagramPacket gamePacket;
	
	private int MAX_PACKET_SIZE = 1024;
	
	//States that are given by the server. Client takes these states to update its game.
	private volatile Queue<AbstractMessage> queueStates = new LinkedList<AbstractMessage>();
	private Deserialization NetTools = new Deserialization();
	
	public ClientBroadcastReceiverUDP(int groupPort, String group) {
		this.groupPort = groupPort;
		this.group = group;
	}
	
	public void run() {
		try {
			BCSocket = new MulticastSocket(groupPort);
			groupID = InetAddress.getByName(group);
			BCSocket.joinGroup(groupID);
			System.out.println("Client UDP Receiver: Activated.");
		} catch (IOException e) {
			System.err.println("Client UDP Receiver: DEACTIVATED. Use another port.");
		}
		
		//Prepare buffer to store packet.
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		gamePacket = new DatagramPacket(buffer,buffer.length);
		
		while(true) {
			try {
				BCSocket.receive(gamePacket);
				System.out.println("Received Packet");
				//Add received message to the queueStates for the client to take.
				byte[] gameBuffer = gamePacket.getData();
				AbstractMessage newMessage = NetTools.deserialize(gameBuffer);
				//testBroadcastReceiver(newMessage);
				//queueStates.add(newMessage);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Error: Can not proccess broadcast packet from server.");
			}
		}
	}
	
	//ClientConnect takes new game state.
	public AbstractMessage getNewState() {
		return queueStates.poll();
	}
	
	public void testBroadcastReceiver(AbstractMessage msg) {
		udpMessage updateMessage = (udpMessage) msg;
		System.out.println(updateMessage.getType());
	}
	
	public String testPacket(DatagramPacket packet) {
		String received = new String(packet.getData(), 0, packet.getLength());
		String[] gameState = received.split(",");
		//System.out.println(received);
		System.out.println(received);
		return received;
	}
	
	public void closeBCSocket(InetAddress group) {
		try {
			BCSocket.leaveGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BCSocket.close();
		return;
	}
	
}
