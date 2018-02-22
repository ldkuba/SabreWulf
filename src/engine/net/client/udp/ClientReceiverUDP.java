package engine.net.client.udp;


import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import engine.net.client.Client;
import engine.net.common_net.UDPTools;
import game.common.config;


public class ClientReceiverUDP extends Thread{
    private static DatagramSocket UDPsocket;
	Client client;
	private float currentNonce;
	NetworkEntity entityUpdateMessage;

    public ClientReceiverUDP(){
        currentNonce = 0;
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
					if(entityUpdateMessage.getNonce()>currentNonce){
                        currentNonce = entityUpdateMessage.getNonce();
					    client.getMain().getNetworkManager().addEntityEvent(entityUpdateMessage);
                    }
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
