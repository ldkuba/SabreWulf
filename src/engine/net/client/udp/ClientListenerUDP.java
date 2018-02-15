package engine.net.client.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.Deserialization;


public class ClientListenerUDP extends Thread{
    private static DatagramSocket UDPsocket;
    private int port;
    private int MAX_PACKET_SIZE;
    
    private Deserialization NetTools = new Deserialization();
    
    ClientListenerUDP(int port, int packetSize) {
    	this.port = port;
    	MAX_PACKET_SIZE = packetSize;
    }
    
    public void run() {
    	try {
			UDPsocket = new DatagramSocket(port);
			System.out.println("Client UDP LISTENER: Activated.");
		} catch (SocketException e1) {
			System.err.println("Client UDP Listener unable to start: Choose another port.");
			e1.printStackTrace();
			return;
		}
    	
    	while(true) {
    		byte[] data = new byte[MAX_PACKET_SIZE];
    		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
    		try {
				UDPsocket.receive(receivePacket);
				
				AbstractMessage gameMessage = NetTools.deserialize(receivePacket.getData());
				
				//Notify client. 'gameMessage'
				
				//System.out.println("Received Packet");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
    
    public void closeUDPListener() {
    	UDPsocket.close();
    	System.out.println("UDP Listener close.");
    	return;
    }
    
    
}
