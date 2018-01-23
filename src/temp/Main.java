package temp;

import engine.Application;

/*
 * 	Only used to test that a window is still loaded
 */

public class Main extends Application {
	
	public static TestState testState;
	public static TestState2 testState2;
	
	public Main()
	{		
		super();
		testState = new TestState(this);
		testState2 = new TestState2(this);
		stateManager.addState(testState);
		stateManager.addState(testState2);
		
		stateManager.setState(testState);
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
