package game.client;

import engine.application.Application;
import engine.net.client.Client;
import engine.net.common_net.networking_messages.LobbyQuitMessage;
import game.client.states.GameState;
import game.client.states.LobbyState;
import game.client.states.MenuState;
import game.common.config;

public class Main extends Application {

	private Client client;

	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	// private PlayerManager playerManager;

	public Main() {
		super(config.screenWidth, config.screenHeight, 1, config.windowName, config.clientFullScreen, false); // window width, window
		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);

		// register all states
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);

		client = new Client(this);
		netManager.registerConnectionListener(new ClientConnectionListener(this));
		netManager.registerMessageListener(new ClientTCPMessageListener(this));

		// set starting state
		stateManager.setCurrentState(gameState);
	}

	public Client getClient() {
		return this.client;
	}

	@Override
	public void cleanup()
	{
		LobbyQuitMessage quit = new LobbyQuitMessage();
		client.sendTCP(quit);
		super.cleanup();
		client.stop();
	}

	public static void main(String[] args) {
		Main game = new Main();
		game.run();
	}
}
