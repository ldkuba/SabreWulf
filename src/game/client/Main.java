package game.client;

import engine.application.Application;
import engine.net.client.Client;
import game.client.states.GameState;
import game.client.states.LobbyState;
import game.client.states.MenuState;

/*
 * 	Main method - used to run the game
 */

public class Main extends Application {
	
	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	//private PlayerManager playerManager;
	
	public Main() {		
		super(1920, 1080, 1, "SabreWulf", false, false); //window width, window height, vsync interval

		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);

		// register all states
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);
		
		// set starting state
		stateManager.setCurrentState(menuState);
	}

	public static void main(String[] args) {
		Main game = new Main();
		game.run();
	}
}
