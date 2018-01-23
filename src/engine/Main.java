package engine;

import temp.TestState;
import temp.TestState2;

/*
 * 	Only used to test that a window is still loaded
 */

public class Main extends Application {
	
	private TestState testState;
	private TestState2 testState2;
	
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
