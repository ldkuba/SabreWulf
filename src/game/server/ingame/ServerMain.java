package game.server.ingame;

import java.util.ArrayList;

import engine.application.Application;
import engine.net.server.core.NetPlayer;
import game.common.player.ActorManager;
import game.server.states.ServerGameState;

public class ServerMain extends Application {
	
	public static ServerGameState gameState;

	//private PlayerManager playerManager;
	
	public ServerMain(ArrayList<NetPlayer> netPlayers){
		super(1920, 1080, 1, "SabreWulf", false, true, netPlayers); //window width, window height, vsync interval
		//System.out.println("starting server engine");
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
