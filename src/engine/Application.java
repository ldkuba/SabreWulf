package engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import engine.input.InputManager;
import engine.state.StateManager;

/*
 * Initialises the game window, creating the application
*/

public class Application {

	protected long window;
	
	protected StateManager stateManager;
	protected InputManager inputManager;
	
	public Application()
	{
		initialiseGLFW();
		
		stateManager = new StateManager(this);
		inputManager = new InputManager(this);
	}
	
	public void initialiseGLFW() {
		// Initialise GLFW
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Create the window
		window = glfwCreateWindow(750, 650, "SabreWulf", NULL, NULL); //width, height
		if (window == NULL) {
			throw new RuntimeException("Failed to create window");
		}

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
	
	public long getWindow()
	{
		return window;
	}
	
	public InputManager getInputManager()
	{
		return inputManager;
	}
}
