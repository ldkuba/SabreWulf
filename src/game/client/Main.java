package game.client;

import engine.application.Application;
import engine.net.client.Client;
import engine.net.common_net.networking_messages.LobbyQuitMessage;
import game.client.states.GameState;
import game.client.states.LobbyState;
import game.client.states.MenuState;

/*
 * 	Main method - used to run the game
 */

public class Main extends Application {

	private Client client;

	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	// private PlayerManager playerManager;

	public Main() {
		super(1280, 720, 1, "SabreWulf", false, false); // window width, window
														// height, vsync
														// interval

		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);

		// register all states
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);

		client = new Client(this);
		netManager.registerConnectionListener(new ClientConnectionListener(this));
		netManager.registerMessageListener(new ClientMessageListener(this));

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
