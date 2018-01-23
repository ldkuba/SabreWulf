package engine.state;

import java.util.ArrayList;
import java.util.Iterator;

import engine.Application;

/*
 * Game State Manager - rather self explanatory name
 */
public class StateManager {
	
	private ArrayList<AbstractState> registered;
	private long window;
	
	private Application app;
	
	private AbstractState activeState;
	
	public StateManager(Application app){
		this.app = app;
		this.window = app.getWindow();
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
				app.getInputManager().removeKeyboardListener(activeState);
				app.getInputManager().removeMouseListener(activeState);
			}
			
			state.init();
			activeState = state;
			
			app.getInputManager().addKeyboardListener(activeState);
			app.getInputManager().addMouseListener(activeState);
			
		} else {
			System.out.println("Error: Switching to unregistered state");
		}
	}
	
}
