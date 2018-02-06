package engine.client.udp;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;

import engine.common_net.AbstractMessage;
import engine.common_net.Serialization;

public class ClientSenderUDP extends Thread{
	
	//Used for testing.
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	
	//Receive messages to send to server.
	private Queue<AbstractMessage> queueMessages = new LinkedList<AbstractMessage>();
	
	//Test Array states
	//private int[] gamePacket = {1,1,0,1,2,1};
	
	private int port;
	private String serverAddress;
	private InetAddress serverIP;
	private static DatagramSocket CSocket;
	private int MAX_PACKET_SIZE;
	
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
		} catch (IOException e){
			System.err.println("Unable to connect to Socket.");
		}
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		while(true) {
			
			if(!queueMessages.isEmpty()) {
				buffer = NetTools.serialize(queueMessages.poll());
				sendPacket(buffer, buffer.length, serverIP, port);
				
			}
			testUDPSender(buffer);
		}	
	}
	
	private void sendPacket(byte[] data, int dataLength, InetAddress serverAddress, int port) {
		
		DatagramPacket packet = new DatagramPacket(data, dataLength, serverAddress, port);
		try {
			CSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testUDPSender(byte[] buffer) {
		System.out.println("Send and int from 0-9");
		String position;
		try {
			position = inFromUser.readLine();
			if (position.length() == 1) {
				buffer = position.getBytes();
				System.out.println(position);
				sendPacket(buffer, buffer.length, serverIP, port);
			} else {
				System.out.println("Invalid input");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMessage(AbstractMessage msg) {
		queueMessages.add(msg);
	}
	
	private void closeSocket() {
		CSocket.close();
	}

}
