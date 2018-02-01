package engine.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientListenerThreadUDP extends Thread{
    private static DatagramSocket UDPsocket;
    private int port;
    private int MAX_PACKET_SIZE;
    private byte[] data = new byte[MAX_PACKET_SIZE];
    
    ClientListenerThreadUDP(int port, int packetSize) {
    	this.port = port;
    	MAX_PACKET_SIZE = packetSize;
    }
    
    public void run() {
    	
    	try {
			UDPsocket = new DatagramSocket(port);
			byte[] data = new byte[MAX_PACKET_SIZE];
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	while(true) {
    		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
    		try {
				UDPsocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
    
}
