package engine.net.client.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import engine.entity.NetworkEntity;
import engine.net.client.Client;
import engine.net.common_net.UDPTools;
import game.common.config;


public class ClientReceiverUDP extends Thread{
    private static DatagramSocket UDPsocket;
	private Client client;
	private int currentId;
	private NetworkEntity entityUpdateMessage;

    public ClientReceiverUDP(){
        currentId = 0;
    }
    
    public void run() {
    	try {
			UDPsocket = new DatagramSocket(config.UDPPort);
		} catch (SocketException e) {

		}
    	
    	while(true) {
    		byte[] data = new byte[config.UDPMaxPacketSize];
    		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
    		try {
				UDPsocket.receive(receivePacket);
				if(receivePacket!=null){

					// Let's see where we put these packets
					entityUpdateMessage = UDPTools.deserialize(receivePacket.getData());
					if(entityUpdateMessage.getPacketId()>currentId || Math.abs(entityUpdateMessage.getPacketId() - currentId) > 1000000 ){
                        currentId = entityUpdateMessage.getPacketId();
					    client.getMain().getNetworkManager().addEntityEvent(entityUpdateMessage);
                    }
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
