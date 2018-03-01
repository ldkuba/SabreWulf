package engine.net.client.udp;


import java.io.IOException;
import java.net.*;

import engine.entity.NetworkEntity;
import engine.net.client.Client;
import engine.net.common_net.NetworkManager;
import engine.net.common_net.UDPTools;
import game.common.config;


public class ClientReceiverUDP extends Thread{
    private static DatagramSocket UDPsocket;
	private Client client;
	private int currentId;
	private NetworkEntity entityUpdateMessage;
	
	private NetworkManager networkManager;

    public ClientReceiverUDP(NetworkManager networkManager){
        currentId = 0;
        this.networkManager = networkManager;
    }
    
    public void run() {
    	try {
			UDPsocket = new DatagramSocket(config.UDPPort);
		} catch (SocketException e){e.printStackTrace();}

		while(true) {
    		byte[] data = new byte[config.UDPMaxPacketSize];
    		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
    		try {
				UDPsocket.receive(receivePacket);
				if(receivePacket!=null){

					// Let's see where we put these packets
					entityUpdateMessage = UDPTools.deserialize(data);
					if(entityUpdateMessage.getPacketId()>currentId || Math.abs(entityUpdateMessage.getPacketId() - currentId) > 1000000 ){
                        currentId = entityUpdateMessage.getPacketId();
					    networkManager.updateEntityInNetworkManager(entityUpdateMessage);
                    }
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
