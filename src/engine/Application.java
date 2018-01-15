package engine;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

/*Initialise the game window 
 * Currently this is basically just the code of the template given   
*/

public class Application {

	private long window;

	public void initialise() {
		// Initialise GLFW
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Create the window
		window = glfwCreateWindow(750, 650, "SabreWulf", NULL, NULL); //width, height
		if (window == NULL) {
			throw new RuntimeException("Failed to create window");
		}
		
		// Setup a key callback - It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // Detected in rendering loop
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Centre the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} 

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);
		// Make the window visible
		glfwShowWindow(window);
	}

	private void loop() {
		GL.createCapabilities();
		// Run the rendering loop until the user has attempted to close the window or has pressed the ESCAPE key.
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer
			glfwSwapBuffers(window); // swap the colour buffers (called after drawing)
			glfwPollEvents(); // Poll for window events. The key callback above will only be invoked during this call.			
		}
	}
	
	public void run() {
		initialise();
		loop();
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		// Terminate GLFW
		glfwTerminate();
		System.err.println("Successfully Quit");
	}

}
