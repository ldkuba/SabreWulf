
package server;
import java.net.*;
import java.net.SocketException;

import server.ingame.IngameServer;
import server.lobby.LobbyServer;

public class MainServer {
	
	private int port;
	private int[] playerIndex;

	//State of Server
	private LobbyServer lobbyState = new LobbyServer();
	private IngameServer inGameState = new IngameServer();
	
	public MainServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) {
		
			MainServer server = new MainServer(6666);
			
			server.startServer();
			System.out.println("It works");
			
	}
	
	public void startServer() {
		
		//Start the lobby. (TCP)
		//Confirm players that are going to play
		//playerIndex = lobbyState.start(port);
		playerIndex = new int[1];
		//			Lobby State -> In-Game State
		
		//Start In-Game. (UDP)
		inGameState.start(playerIndex, port);
		
	}
	
	public void close() {
		
	}

}
