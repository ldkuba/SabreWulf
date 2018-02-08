package game;

import engine.application.Application;
import game.client.Client;
import game.states.*;

/*
 * 	Main method - used to run the game
 */

public class Main extends Application {
	
	Client client;
	
	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	
	public Main() {		
		super(800, 600, 1, "SabreWulf"); //window width, window height, vsync interval
		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);
		
		//register all states
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);
				
		//set starting state 
		stateManager.setCurrentState(gameState);
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
