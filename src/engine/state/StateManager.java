package engine.state;

import java.util.ArrayList;

import engine.application.Application;

/*
 * Game State Manager
 */
public class StateManager {

	private Application app;
	private ArrayList<AbstractState> registered;
	private AbstractState activeState;

	public StateManager(Application app) {
		this.app = app;
		registered = new ArrayList<AbstractState>();
	}

	public void addState(AbstractState newState) {
		// register new state if it doesn't already exist
		if (!isRegistered(newState)) {
			registered.add(newState);
		} else {
			System.err.println("State already registered");
		}
	}

	public boolean isRegistered(AbstractState state) {
		// checks to see if given state is registered
		if (registered.contains(state)) {
			return true;
		} else {
			return false;
		}
	}

	public void updateState() {
		//update active state 
		activeState.update();
	}

	public void renderState() {
		//render active state
		activeState.render();
	}

	public void setCurrentState(AbstractState state) {
		// switch to a specified game state if it exists
		if (isRegistered(state)) {
			if (activeState != null) {
				app.getInputManager().removeKeyboardListener(activeState);
				app.getInputManager().removeMouseListener(activeState);
				activeState.deactivate();	
			}
			state.init();
			activeState = state;
			app.getInputManager().addKeyboardListener(activeState);
			app.getInputManager().addMouseListener(activeState);
		} else {
			System.err.println("Error: Trying to switch to an unregistered state");
		}
	}

}
