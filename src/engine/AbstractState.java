package engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

/*
 * AbstractState is a class that is extended by all different
 * game states
 */
public abstract class AbstractState {
	
	public abstract void init(); //initialise state
	public abstract void render(); //draw state
	public abstract void update(); //update state
	
	//Create a keyboard listener (this one probably isn't great) - still needs to be implemented 
	class KeyboardListener extends GLFWKeyCallback{
		public boolean[] keys = new boolean[65535];
		  @Override
		  public void invoke(long window, int key, int scancode, int action, int mods) {
		    keys[key] = action != GLFW_RELEASE;
		  }
		  
		  // boolean method that returns true if a given key is pressed.
		  public boolean isKeyDown(int keycode) {
			  return keys[keycode];
		  }		
	}
	
	//Mouse Listener
}
