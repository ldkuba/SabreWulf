package engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import input.KeyboardHandler;

/*
 * AbstractState is a class that is extended by all different
 * game states
 */
public abstract class AbstractState extends KeyboardHandler {
	private GLFWKeyCallback keyCallback;
	
	public abstract void init(); //initialise state
	public abstract void render(); //draw state
	public abstract void update(); //update state
	public abstract void deactivate(); //opposite to initialize
	    
}
