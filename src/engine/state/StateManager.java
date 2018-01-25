package engine.state;

import java.util.ArrayList;

import engine.Application;

/*
 * Game State Manager
 */
public class StateManager {

	private Application app;
	private ArrayList<AbstractState> registered;
	private AbstractState activeState;
	//private long window;

	public StateManager(Application app) {
		this.app = app;
		//this.window = app.getWindow();
		registered = new ArrayList<AbstractState>();
	}

	public void addState(AbstractState newState) {
		// register new state if it doesnt already exist
		if (!registered.contains(newState)) {
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
		//update a state - if its active
		for (AbstractState state : registered) {
			if (state.equals(activeState))
				state.update();
		}
	}

	public void renderState() {
		//render a state - if its active
		for (AbstractState state : registered) {
			if (state.equals(activeState)) {
				state.render();
			}
		}
	}

	public void setCurrentState(AbstractState state) {
		// switch to a specified game state if it exists
		if (isRegistered(state)) {
			if (activeState != null) {
				activeState.deactivate();
				app.getInputManager().removeKeyboardListener(activeState);
				app.getInputManager().removeMouseListener(activeState);
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
