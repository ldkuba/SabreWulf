package game;

import engine.application.Application;

/*
 * 	Only used to test that a window is still loaded
 */

public class Main extends Application {
	
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
	}
	
	public static void main(String[] args){
		Main game = new Main();		
		game.run();
	}
}
