package game;

import engine.application.Application;
import game.client.Client;

/*
 * 	Only used to test that a window is still loaded
 */

public class  Main extends Application {

	Client client;

	public static game.TestState testState;
	public static game.TestState2 testState2;
	
	public Main() {

		super(750, 650, 1, "SabreWulf"); //window width, window height, vsync interval

		client = new Client();
		testState = new game.TestState(this);
		testState2 = new game.TestState2(this);
		stateManager.addState(testState);
		stateManager.addState(testState2);

		
		//set starting state 
		stateManager.setCurrentState(testState);

		// provisional invocation of the client (by Mihai)
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
