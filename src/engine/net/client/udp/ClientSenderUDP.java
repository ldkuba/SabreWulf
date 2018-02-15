package engine.net.client.udp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import engine.net.common_net.AbstractMessage;
import engine.net.common_net.Serialization;

public class ClientSenderUDP extends Thread{
	
	//Used for testing.
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	
	//Receive messages to send to server.
	private volatile Queue<AbstractMessage> queueMessages = new LinkedList<AbstractMessage>();
	
	//Test Array states
	//private int[] gamePacket = {1,1,0,1,2,1};
	
	private int port;
	private String serverAddress;
	private InetAddress serverIP;
	private static DatagramSocket CSocket;
	private int MAX_PACKET_SIZE;
	
	private String testMessage = "Hello, GameServer";
	
	private Serialization NetTools = new Serialization();
	
	public ClientSenderUDP(int port, String serverAddress, int packetSize) {
		this.port = port;
		this.serverAddress = serverAddress;
		MAX_PACKET_SIZE = packetSize;
	}
	
	public void run() {
		try {
		CSocket = new DatagramSocket();
		serverIP = InetAddress.getByName(serverAddress);
		System.out.println("Client UDP Sender: Activated.");
		} catch (IOException e){
			System.err.println("Client UDP Sender: DEACTIVATED -> GameServer or port unavailable.");
			System.err.println("Closing Client Sender.");
			return;
		}
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		buffer = testMessage.getBytes();
		
		while(true) {
			//Sends packets in queue
			if(!queueMessages.isEmpty()) {
				AbstractMessage messageToSend = queueMessages.poll();
				buffer = NetTools.serialize(messageToSend);
				sendPacket(buffer, buffer.length, serverIP, port);
			}
			
		}	
	}
	
	private void sendPacket(byte[] data, int dataLength, InetAddress serverAddress, int port) {
		
		DatagramPacket packet = new DatagramPacket(data, dataLength, serverAddress, port);
		try {
			CSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to send packet.");
			e.printStackTrace();
		}
	}
	
	public void addMessage(AbstractMessage msg) {
		//Client adds messages onto the queueMessages to be sent over to the server
		queueMessages.add(msg);
	}
	
	private void closeSocket() {
		CSocket.close();
		return;
	}

}
