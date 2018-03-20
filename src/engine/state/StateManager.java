package engine.state;

import java.util.ArrayList;

import engine.application.Application;

/**
 * Manage the states that are registered to the game
 * @author bhavi
 *
 */
public class StateManager {

	private Application app;
	private ArrayList<AbstractState> registered;
	private AbstractState activeState;
	
	/**
	 * Create a new state manager
	 * @param app
	 */
	public StateManager(Application app) {
		this.app = app;
		registered = new ArrayList<AbstractState>();
	}

	/**
	 * Register a new state if it doesn't already exist
	 * @param newState
	 */
	public void addState(AbstractState newState) {
		if (!isRegistered(newState)) {
			registered.add(newState);
		} else {
			System.err.println("State already registered");
		}
	}

	/**
	 * Check to see whether a given state is already registered
	 * @param state
	 * @return
	 */
	public boolean isRegistered(AbstractState state) {
		if (registered.contains(state)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Update the active state
	 */
	public void updateState() {
		activeState.update();
	}

	/**
	 * Render the active state
	 */
	public void renderState() {
		activeState.render();
	}

	/**
	 * Set the current state - used to switch between states
	 * @param state
	 */
	public void setCurrentState(AbstractState state) {
		if (isRegistered(state)) {
			if (activeState != null) {
				if(!app.isHeadless()) {
					app.getInputManager().removeKeyboardListener(activeState);
					app.getInputManager().removeMouseListener(activeState);
				}
				activeState.deactivate();
			}
			state.init();
			activeState = state;
			if(!app.isHeadless()) {
				app.getInputManager().addKeyboardListener(activeState);
				app.getInputManager().addMouseListener(activeState);
			}
		} else {
			System.err.println("Error: Trying to switch to an unregistered state");
		}
	}

}
