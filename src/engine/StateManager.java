package engine;

import java.util.Iterator;
import java.util.ArrayList;

/*
 * Game State Manager - rather self explanatory name
 */
public class StateManager {
	ArrayList<AbstractState> registered;
	long window;
	
	private AbstractState activeState;
	
	public StateManager(long window){
		this.window = window;
		registered = new ArrayList<AbstractState>();
	}
	
	public void addState(AbstractState newState){
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
	
	public boolean isRegistered(AbstractState state){
		//checks to see if given state is registered
		if(registered.contains(state)){
			return true;
		} else {
			return false;
		}
	}
	
	public void updateState()
	{
		for(AbstractState state : registered)
		{
			if(state.equals(activeState))
				state.update();
		}
	}
	
	public void renderState()
	{
		for(AbstractState state : registered)
		{
			if(state.equals(activeState))
				state.render();
		}
	}
	
	public void setState(AbstractState state){
		System.out.println("Hello");
		//switch to a specified game state if it exists
		
		if (isRegistered(state)){
			
			if(activeState != null)
			{
				activeState.deactivate();
			}
			
			state.init();
			activeState = state;
		} else {
			System.out.println("Error: Switching to unregistered state");
		}
	}
	
}
