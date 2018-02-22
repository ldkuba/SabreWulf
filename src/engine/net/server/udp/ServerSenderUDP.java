package engine.net.server.udp;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.entity.NetworkEntity;
import engine.net.common_net.Port;
import engine.net.common_net.UDPTools;
import engine.net.server.core.Player;

public class ServerSenderUDP extends Thread{

	private BlockingQueue<NetworkEntity> queueMessages;
	private ArrayList<Player> players;
	private int port;
	DatagramPacket packet;
	private int MAX_PACKET_SIZE;
	private int packetId;

	public ServerSenderUDP(ArrayList<Player> players) {
		this.players = players;
		this.queueMessages = new LinkedBlockingQueue<NetworkEntity>();
		this.port = port;
		MAX_PACKET_SIZE = 500;
	}
	
	public void addNetworkEntity(NetworkEntity entity)
	{
		entity.setPacketId(packetId);
		queueMessages.add(entity);
		
		packetId++;
		
		if(packetId > 2000000)
		{
			packetId = 0;
		}
	}
	
	public void run() {
		for(int i=0; i<players.size(); i++){
			try {
				players.get(i).generateDatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}


		byte[] buffer = new byte[MAX_PACKET_SIZE];
		
		while(true) {
			//Sends packets in queue
			if(!queueMessages.isEmpty()) {
				NetworkEntity messageToSend = null;
				try {
					messageToSend = queueMessages.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				buffer = UDPTools.serialize(messageToSend);

				for(int i=0; i<players.size(); i++){
					packet = new DatagramPacket(buffer, buffer.length, players.get(i).getSocket().getInetAddress(), Port.UDPPort);
					try {
						players.get(i).getDatagramSocket().send(packet);
					} catch (IOException e) {
						continue;
					}
				}
			}
		}	
	}
}
