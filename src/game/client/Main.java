package game.client;

import engine.application.Application;
import engine.net.client.Client;
import engine.net.common_net.networking_messages.LobbyQuitMessage;
import game.client.states.EndState;
import game.client.states.GameState;
import game.client.states.LobbyState;
import game.client.states.MenuState;
import game.common.config;

/**
 * The main method used to run the game
 * @author SabreWulf
 *
 */
public class Main extends Application {

	public static String clientName;

	public static MenuState menuState;
	public static LobbyState lobbyState;
	public static GameState gameState;
	public static EndState endState;
	
	private Client client;

	/**
	 * Create a new main. this is used to initialise the game states and register the newly connected client
	 */
	public Main() {
		super(config.screenWidth, config.screenHeight, 1, config.windowName, config.clientFullScreen, false); // window width, window
		menuState = new MenuState(this);
		lobbyState = new LobbyState(this);
		gameState = new GameState(this);
		endState = new EndState(this);
		// register all states
		stateManager.addState(menuState);
		stateManager.addState(lobbyState);
		stateManager.addState(gameState);
		stateManager.addState(endState);

		client = new Client(this);
		netManager.registerConnectionListener(new ClientConnectionListener(this));
		netManager.registerMessageListener(new ClientTCPMessageListener(this));

		// set starting state
		stateManager.setCurrentState(menuState);
	}

	/**
	 * Get the client that is running the application 
	 * @return
	 */
	public Client getClient() {
		return this.client;
	}

	/**
	 * Cleanup by stopping the network
	 */
	@Override
	public void cleanup()
	{
		LobbyQuitMessage quit = new LobbyQuitMessage();
		client.sendTCP(quit);
		super.cleanup();
		client.stop();
	}

	/**
	 * Run main
	 * @param args
	 */
	public static void main(String[] args) {
		Main game = new Main();
		game.run();
	}
}
