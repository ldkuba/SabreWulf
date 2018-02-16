package game.server.states;

import engine.application.Application;
import game.client.Main;
import game.server.states.ServerGameState;

public class ServerMain extends Application {
	
	public static ServerGameState gameState;
	//private PlayerManager playerManager;
	
	public ServerMain() {		
		super(1920, 1080, 1, "SabreWulf", false, true); //window width, window height, vsync interval

	}

	public void run(){
		gameState = new ServerGameState(this);

		stateManager.addState(gameState);

		// set starting state
		stateManager.setCurrentState(gameState);
		Main game = new Main();
		game.run();
	}
}
