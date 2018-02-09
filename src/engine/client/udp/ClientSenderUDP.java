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

import game.networking.ExampleMessage;
import engine.common_net.AbstractMessage;
import engine.common_net.Serialization;

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
	
	private void testUDPSender(byte[] buffer) {
		//Do not expect response from server.
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
		//Client adds messages onto the queueMessages to be sent over to the server
		queueMessages.add(msg);
	}
	
	private void closeSocket() {
		CSocket.close();
		this.interrupt();
	}

}
