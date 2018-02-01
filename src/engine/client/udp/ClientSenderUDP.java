package engine.client.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSenderUDP extends Thread{
	
	private int port;
	private InetAddress serverIP;
	private static DatagramSocket CSocket;
	private int MAX_PACKET_SIZE;
	
	public ClientSenderUDP(int port, InetAddress serverIP, int packetSize) {
		this.port = port;
		this.serverIP = serverIP;
		MAX_PACKET_SIZE = packetSize;
	}
	
	public void run() {
		try {
		CSocket = new DatagramSocket();
		} catch (IOException e){
			System.err.println("Unable to connect to Socket.");
		}
		byte[] buffer = new byte[1024];
		while(true) {
			//Bombard the server with packets
			sendPacket(buffer, buffer.length, serverIP, port);
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
	
	private void closeSocket() {
		CSocket.close();
	}

}
