package game;

import engine.application.Application;
import game.client.Client;
import game.states.TestState;
import game.states.TestState2;

/*
 * 	Only used to test that a window is still loaded
 */

public class  Main extends Application {

	Client client;

	public static TestState testState;
	public static TestState2 testState2;
	
	public Main() {

		super(750, 650, 1, "SabreWulf"); //window width, window height, vsync interval
		testState = new TestState(this);
		testState2 = new TestState2(this);
		stateManager.addState(testState);
		stateManager.addState(testState2);

		
		//set starting state 
		stateManager.setCurrentState(testState);

		// provisional invocation of the client (by Mihai)
		client = new Client();
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
