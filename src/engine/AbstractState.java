package engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import input.KeyboardListener;
import input.MouseListener;

/*
 * AbstractState is a class that is extended by all different
 * game states
 */
public abstract class AbstractState implements KeyboardListener, MouseListener {
	
	public abstract void init(); //initialise state
	public abstract void render(); //draw state
	public abstract void update(); //update state
	public abstract void deactivate(); //opposite to initialize
	
}
