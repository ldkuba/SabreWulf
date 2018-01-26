package temp;

import engine.Application;

/*
 * 	Only used to test that a window is still loaded
 */

public class Main extends Application {
	
	public static TestState testState;
	public static TestState2 testState2;
	
	public Main() {		
		super(750, 650); //pass through width and height of window
		testState = new TestState(this);
		testState2 = new TestState2(this);
		stateManager.addState(testState);
		stateManager.addState(testState2);
		
		//set starting state 
		stateManager.setCurrentState(testState);
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
