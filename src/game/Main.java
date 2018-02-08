package game;

import engine.application.Application;
import game.client.Client;

/*
 * 	Only used to test that a window is still loaded
 */

public class Main extends Application {
	
	Client client;
	
	
	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	
	public Main() {
		super(750, 650, 1, "SabreWulf"); //window width, window height, vsync interval
		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);

		
		//set starting state 
		stateManager.setCurrentState(gameState);

		client = new Client();
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
