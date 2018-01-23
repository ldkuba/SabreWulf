package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

/*
 * Initialises the game window, creating the application
*/

public class Application {

	protected long window;
	
	protected StateManager stateManager;
	
	public Application()
	{
		initialise();
	}
	
	public void initialise() {
		// Initialise GLFW
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Create the window
		window = glfwCreateWindow(750, 650, "SabreWulf", NULL, NULL); //width, height
		if (window == NULL) {
			throw new RuntimeException("Failed to create window");
		}
		
		// Setup a key callback - if esc is pressed the program is terminated
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // Detected in loop()
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);// Centre the window
		} 

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
		// Make the window visible
		glfwShowWindow(window);
		
		stateManager = new StateManager(window);
	}

	public void run() {
		
		GL.createCapabilities();
		// Run the rendering loop until the user presses esc or quits
		while (!glfwWindowShouldClose(window)) {
			
			stateManager.updateState();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer
		
			stateManager.renderState();
			
			glfwSwapBuffers(window); // swap the colour buffers (called after drawing)
			glfwPollEvents(); // Poll for window events. The key callback above will only be invoked during this call.	
			//states.switchToState(null);
		}
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		// Terminate GLFW
		glfwTerminate();
		System.err.println("Successfully Quit");
	}
	
	public StateManager getStateManager()
	{
		return stateManager;
	}
}
