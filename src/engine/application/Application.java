package engine.application;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import engine.input.InputManager;
import engine.state.StateManager;

/*
 * Initialise and terminate the application window
*/

public class Application {

	protected long window;
	protected StateManager stateManager;
	protected InputManager inputManager;
	private GLFWWindowSizeCallback windowSizeCallback;
	private boolean isFullScreen; 
	
	public Application(int width, int height, int vsyncInterval, String name) {
		initialise(width, height, vsyncInterval, name);
		stateManager = new StateManager(this);
		inputManager = new InputManager(this);
	}

	public void initialise(int width, int height, int vsyncInterval, String name) {
		// Initialise GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		if(vidmode.width() == width && vidmode.height() == height){
			isFullScreen = true;
			glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		}
		// Create the window
		window = glfwCreateWindow(width, height, name, NULL, NULL); // width, height, window name
		if (window == NULL) {
			throw new RuntimeException("Failed to create window");
		}
		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			// Centre the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(vsyncInterval);
		// Make the window visible
		glfwShowWindow(window);

		GL.createCapabilities();
	}

	public void run() {
		// Run the rendering loop until the user presses esc or quits
		while (!glfwWindowShouldClose(window)) {
			stateManager.updateState();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear frame
																// buffer
			stateManager.renderState();
			glfwSwapBuffers(window); // swap the colour buffers
			glfwPollEvents(); // Poll for window events. The key callback above will only be invoked during this call.
		}
		cleanup();
	}

	public StateManager getStateManager() {
		return stateManager;
	}

	public long getWindow() {
		// Needed in order to have the same window over all states
		return window;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public void exit() {
		glfwSetWindowShouldClose(window, true);
	}

	public void cleanup() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		// Terminate GLFW
		glfwTerminate();
		System.out.println("Successfully Quit");
	}

	public void resize(long window, int width, int height) {
		if(!isFullScreen){
			glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
				@Override
				public void invoke(long window, int width, int height) {
					System.out.println("invoked window size callback");
					glfwSetWindowSizeCallback(window, windowSizeCallback);
				};
			});
		}
	}
}