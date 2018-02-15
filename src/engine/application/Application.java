package engine.application;


import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import engine.assets.AssetManager;
import engine.gui.GUI;
import engine.input.InputManager;
import engine.maths.Vec2;
import engine.state.StateManager;
import game.method.MethodExecutor;

/*
 * Initialise and terminate the application window
*/

public class Application
{
	protected NetworkManager netManager;
	boolean networkType;

	protected boolean isHeadless;
	protected long window;
	protected StateManager stateManager;
	protected InputManager inputManager;
	protected AssetManager assetManager;
	protected GUI gui;

	protected GLFWWindowSizeCallback windowSizeCallback;
	public static Vec2 s_WindowSize;
	public static Vec2 s_Viewport;
	protected boolean isFullScreen;

	public Application(int width, int height, int vsyncInterval, String name, boolean fullscreen, boolean headless)
	{
		isHeadless = headless;
		if(!headless){

			initialise(width, height, vsyncInterval, name, fullscreen);
			inputManager = new InputManager(this);
			assetManager = new AssetManager();
			gui = new GUI(this);
		}

		netManager = new NetworkManager(networkType, this);
		
		stateManager = new StateManager(this);
		
		setViewport(10.0f*(s_WindowSize.getX()/s_WindowSize.getY()), 10.0f);
	}

	public void initialise(int width, int height, int vsyncInterval, String name, boolean fullscreen)
	{
		// Initialise GLFW
		if(!glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		isFullScreen = fullscreen;
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		if(!isFullScreen)
		{
			glfwWindowHint(GLFW_RESIZABLE, GLFW.GLFW_TRUE);
			// Create the window
			window = glfwCreateWindow(width, height, name, NULL, NULL); // width, height, window name
			setResolution(width, height);
		}else
		{
			glfwWindowHint(GLFW_RESIZABLE, GLFW.GLFW_FALSE);
			window = glfwCreateWindow(vidmode.width(), vidmode.height(), name, GLFW.glfwGetPrimaryMonitor(), NULL);
			setResolution(vidmode.width(), vidmode.height());
		}

		if(window == NULL)
		{
			throw new RuntimeException("Failed to create window");
		}
		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush())
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			// Centre the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		glfwMakeContextCurrent(window);
		// set resizeCallback
		glfwSetWindowSizeCallback(window, windowSizeCallback);
		// Enable v-sync
		glfwSwapInterval(vsyncInterval);
		// Enable Antialiasing
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
		// Make the window visible

		glfwShowWindow(window);

		GL.createCapabilities();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void run()
	{
		// Run the rendering loop until the user presses esc or quits
		while (!glfwWindowShouldClose(window))
		{
			stateManager.updateState();
			gui.update();			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear frame
																// buffer
			stateManager.renderState();
			glfwSwapBuffers(window); // swap the colour buffers
			glfwPollEvents(); // Poll for window events. The key callback above
								// will only be invoked during this call.
		}
		cleanup();
	}

	public StateManager getStateManager()
	{
		return stateManager;
	}

	public long getWindow()
	{
		// Needed in order to have the same window over all states
		return window;
	}

	public InputManager getInputManager()
	{
		return inputManager;
	}

	public AssetManager getAssetManager()
	{
		return assetManager;
	}
	
	public GUI getGui()
	{
		return gui;
	}
	
	public void exit()
	{
		glfwSetWindowShouldClose(window, true);
	}

	public void cleanup()
	{
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		// Terminate GLFW
		glfwTerminate();
		System.out.println("Successfully Quit");
	}
	
	public void setResolution(int width, int height)
	{
		s_WindowSize = new Vec2(width, height);
	}
	
	public void setViewport(float right, float top)
	{
		s_Viewport = new Vec2(right, top);
	}

	public void resize(long window, int width, int height)
	{
		if(!isFullScreen)
		{
			glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback()
			{
				@Override
				public void invoke(long window, int width, int height)
				{
					setResolution(width, height);
				};
			});
		}
	}
}