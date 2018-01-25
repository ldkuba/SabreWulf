package engine.state;

import engine.input.KeyboardListener;
import engine.input.MouseListener;

/*
 * AbstractState is a class that is extended by all different
 * game states
 */

public abstract class AbstractState implements KeyboardListener, MouseListener {
	
	public abstract void init(); //initialise state
	public abstract void render(); //draw state
	public abstract void update(); //update state
	public abstract void deactivate(); //opposite to initialise
	
}
