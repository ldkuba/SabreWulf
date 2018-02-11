package game;

import engine.application.Application;
import game.client.Client;
import game.player.PlayerManager;
import game.states.GameState;
import game.states.LobbyState;
import game.states.MenuState;

/*
 * 	Only used to test that a window is still loaded
 */

public class Main extends Application {
	
	Client client;
	
	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	public static PlayerManager playerManager;
	
	public Main() {		
		super(800, 600, 1, "SabreWulf"); //window width, window height, vsync interval
		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);

		client = new Client();
		
		
		//set starting state 
		stateManager.setCurrentState(gameState);
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
