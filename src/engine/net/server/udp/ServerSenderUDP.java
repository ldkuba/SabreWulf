package engine.net.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import engine.net.common_net.Synchronizable;
import engine.net.common_net.UDPTools;
import engine.net.server.core.Player;

public class ServerSenderUDP extends Thread{

	private volatile Queue<Synchronizable> queueMessages;
	private ArrayList<Player> players;
	private int port;
	DatagramPacket packet;
	private int MAX_PACKET_SIZE;


	public ServerSenderUDP(int port, ArrayList<Player> players) {
		queueMessages = new LinkedList<Synchronizable>();
		this.port = port;
		MAX_PACKET_SIZE = 500;
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
				Synchronizable messageToSend = queueMessages.poll();
				buffer = UDPTools.serialize(messageToSend);

				for(int i=0; i<players.size(); i++){
					packet = new DatagramPacket(buffer, buffer.length, players.get(i).getSocket().getInetAddress(), port);
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
