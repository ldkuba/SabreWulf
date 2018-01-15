package engine;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

/*
 * AbstractState is a class that is extended by all different
 * game states 
 */
public abstract class AbstractState {
	
	public abstract void initialize();
	public abstract void render();
	public abstract void update();
	
	//Create a keyboard listener (this one probably isn't great)
	class KeyboardListener extends GLFWKeyCallback{
		public boolean[] keys = new boolean[65536];

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
