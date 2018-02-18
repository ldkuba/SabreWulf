package game.server.states;

import engine.application.Application;
import engine.net.common_net.NetworkManager;
import engine.net.server.core.GameInstanceManager;
import game.client.ClientConnectionListener;
import game.client.Main;
import game.server.ingame.ServerConnectionListener;
import game.server.ingame.ServerMessageListener;
import game.server.states.ServerGameState;

public class ServerMain extends Application {
	
	public static ServerGameState gameState;


	//private PlayerManager playerManager;
	
	public ServerMain(){
		super(1920, 1080, 1, "SabreWulf", false, true); //window width, window height, vsync interval
		System.out.println("starting server engine");
		gameState = new ServerGameState(this);

		stateManager.addState(gameState);

		netManager.registerMessageListener(new ServerMessageListener(this));
		netManager.registerConnectionListener(new ServerConnectionListener(this));
		// set starting state
		stateManager.setCurrentState(gameState);
	}
	@Override
	public void cleanup(){
		super.cleanup();
	}

}
