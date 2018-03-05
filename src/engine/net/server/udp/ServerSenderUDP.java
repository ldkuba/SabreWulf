package engine.net.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.entity.NetworkEntity;
import engine.net.common_net.UDPTools;
import engine.net.server.core.NetPlayer;
import game.common.config;

public class ServerSenderUDP extends Thread{

	private BlockingQueue<NetworkEntity> queueMessages;
	private ArrayList<NetPlayer> players;
	private int port;
	private DatagramPacket packet;
	private DatagramSocket udpSocket;
	private int packetId;

	public ServerSenderUDP(ArrayList<NetPlayer> players) {
		this.players = players;
		this.queueMessages = new LinkedBlockingQueue<NetworkEntity>();
		this.port = port;
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
		
		try
		{
			udpSocket = new DatagramSocket(config.ServerUDPPort);
		}catch (SocketException e1)
		{
			e1.printStackTrace();
		}
		
		byte[] buffer = new byte[config.UDPMaxPacketSize];
		
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
					packet = new DatagramPacket(buffer, buffer.length, players.get(i).getSocket().getInetAddress(), config.UDPPort);
					try {
						udpSocket.send(packet);
					} catch (IOException e) {
						continue;
					}
				}
			}
		}	
	}
}
