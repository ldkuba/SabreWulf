package engine;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Game State Manager - rather self explanatory name
 */
public class StateManager {
	ArrayList<AbstractState> registered;
	private GameState activeState;
	
	public StateManager(){
		registered = new ArrayList<AbstractState>();
	}
	
	private enum GameState {
		MENU, LOBBY, GAME, QUIT
	}
	
	private void addState(AbstractState newState){
		registered.add(newState);		
	}
	
	private void printStates(){
		//print registered states - for testing 
		Iterator<AbstractState> itr = registered.iterator();
		while(itr.hasNext()){
			String elem = itr.next().toString();
			System.out.print(elem + " /n");
		}
	}
	
	private boolean isRegistered(AbstractState state){
		//checks to see if given state is registered
		if(registered.contains(state)){
			return true;
		} else {
			return false;
		}
	}
	
	private void switchToState(AbstractState state){
		//switch to a specified game state if it exists
		if (isRegistered(state)){
			state.init();
		} else {
			System.err.println("Cannot switch to state, it does not exist");
		}
	}
}
