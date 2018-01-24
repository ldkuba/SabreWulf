package server.lobby;

import java.io.IOException;
import java.net.*;

import server.AbstractListener;

/*
 * TCP Connection
 * 
 * 
 * 
 */


public class LobbyServer extends AbstractListener{
	
	private int[] players;
	private int port;
	private int maxPlayers;
	private ServerSocket serverSocket;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int[] start(int port) {
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error: Unable to start server");
			System.exit(1);	//Don't even bother
		}
		
		//Placeholder code
		players = new int[10];
		return players;
		
	}
	
	protected void close() {
		
	}
	
	public void listen() {
		
		//Initialized as a thread.
		
		int currentPlayers = 0;
		
		while(true) {
			
			//Listen to sockets.
			try {
				Socket socket = serverSocket.accept();
				
				//Ask for name of client
				
				/*Create thread for client*/
				//(new ServerReceiver(clientName,fromClient,clientTable,regisUserTable,terminalTable,groupTable, clientName,socket)).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}


}
