package server.ingame;

import java.io.IOException;
import java.net.*;

import server.AbstractListener;

/*
 * UDP Connection
 * 
 */

public class IngameServer extends AbstractListener {
	
	int[] players;
	int port;
	
	protected DatagramSocket socket;
	
	int MAX_PACKET_SIZE = 1024;
	
	public IngameServer() {
		
	}
	
	//Initialize the UDP connection
	public void start(int[] players, int port) {
		
		this.players = players;
		this.port = port;
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Error: Unable to create server socket");
			return;
		}
		
		while(true) {
			listen();

		}
		
	}
	
	//Receive UDP-packets
	public void listen() {
		
		//Initialized as a thread.
		
		byte[] data = new byte[MAX_PACKET_SIZE];
		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
		
		while(true) {
			try {
				socket.receive(receivePacket);
				
				testConnection(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void testConnection(DatagramPacket packet) {
		
		byte[] sendData = new byte[1024];
		
		String message = new String(packet.getData());
		System.out.println("RECEIVED: " + message);
		
		InetAddress IPAddress = packet.getAddress();
		int port = packet.getPort();
		String capitalizedMessage = message.toUpperCase();
		sendData = capitalizedMessage.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void sendDataM(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		System.out.println("Now closing socket");
		socket.close();
	}


}
