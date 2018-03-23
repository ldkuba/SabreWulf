package engine.application;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;

import engine.assets.AssetManager;
import engine.gui.GUI;
import engine.input.InputManager;
import engine.maths.Vec2;
import engine.net.common_net.NetworkManager;
import engine.net.server.core.NetPlayer;
import engine.sound.SoundManager;
import engine.state.StateManager;

/**
 * Initialises and terminate the application window. It also sets up the various
 * managers. This class is extended by Main.java.
 * 
 * @author SabreWulf
 *
 */

public class Application {

	public static Vec2 s_WindowSize;
	public static Vec2 s_Viewport;

	protected NetworkManager netManager;
	protected StateManager stateManager;
	protected InputManager inputManager;
	protected AssetManager assetManager;
	protected SoundManager soundManager;
	protected GUI gui;
	protected Timer timer;
	protected GLFWWindowSizeCallback windowSizeCallback;

	protected boolean networkType;
	protected boolean isHeadless;
	protected long window;
	protected boolean isFullScreen;

	private Callback debugProc;

	private boolean running = true;

	/**
	 * Create a new application window and initialise all of the respective
	 * managers and the timer - without net players
	 * 
	 * @param width
	 * @param height
	 * @param vsyncInterval
	 * @param name
	 * @param fullscreen
	 * @param headless
	 */
	public Application(int width, int height, int vsyncInterval, String name, boolean fullscreen, boolean headless) {
		networkType = false;
		isHeadless = headless;
		if (!headless) {

			initialise(width, height, vsyncInterval, name, fullscreen);
			inputManager = new InputManager(this);
			assetManager = new AssetManager();
			soundManager = new SoundManager();
			gui = new GUI(this);
			setViewport(10.0f * (s_WindowSize.getX() / s_WindowSize.getY()), 10.0f);
			netManager = new NetworkManager(this);
		} else {
			netManager = new NetworkManager(this);
		}

		timer = new Timer(60.0f);
		stateManager = new StateManager(this);
	}

	/**
	 * Create a new application window and initialise all of the respective
	 * managers and the timer - with net players
	 * 
	 * @param width
	 * @param height
	 * @param vsyncInterval
	 * @param name
	 * @param fullscreen
	 * @param headless
	 * @param netPlayers
	 */
	public Application(int width, int height, int vsyncInterval, String name, boolean fullscreen, boolean headless,
			ArrayList<NetPlayer> netPlayers) {	
		networkType = true;
		isHeadless = headless;
		if (!headless) {

			initialise(width, height, vsyncInterval, name, fullscreen);
			inputManager = new InputManager(this);
			assetManager = new AssetManager();
			soundManager = new SoundManager();
			gui = new GUI(this);
			setViewport(10.0f * (s_WindowSize.getX() / s_WindowSize.getY()), 10.0f);
			netManager = new NetworkManager(netPlayers, this);
		} else {
			netManager = new NetworkManager(netPlayers, this);
		}

		timer = new Timer(60.0f);
		stateManager = new StateManager(this);

	}

	/**
	 * Initialise the window
	 * 
	 * @param width
	 * @param height
	 * @param vsyncInterval
	 * @param name
	 * @param fullscreen
	 */
	public void initialise(int width, int height, int vsyncInterval, String name, boolean fullscreen) {
		// Initialise GLFW
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		isFullScreen = fullscreen;

		Configuration.DEBUG.set(true);

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		if (!isFullScreen) {

			glfwWindowHint(GLFW_RESIZABLE, GLFW.GLFW_TRUE);
			// Create the window
			window = glfwCreateWindow(width, height, name, NULL, NULL);
			setResolution(width, height);
		} else {

			glfwWindowHint(GLFW_RESIZABLE, GLFW.GLFW_FALSE);
			window = glfwCreateWindow(vidmode.width(), vidmode.height(), name, GLFW.glfwGetPrimaryMonitor(), NULL);
			setResolution(vidmode.width(), vidmode.height());
		}

		// before context creation
		glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);

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
		glfwMakeContextCurrent(window);
		// set resizeCallback
		glfwSetWindowSizeCallback(window, windowSizeCallback);
		// Enable v-sync
		glfwSwapInterval(vsyncInterval);
		// Enable Antialiasing
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 2);
		// Make the window visible
		glfwShowWindow(window);

		GL.createCapabilities();

		debugProc = GLUtil.setupDebugMessageCallback(); // may return null if
														// the debug mode is not
														// available

		if (debugProc == null) {
			System.out.println("NULL DEBUG PROCESSOR");
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Run the application
	 */
	public void run() {
		if (!isHeadless) {
			// Run the rendering loop until the user presses esc or quits
			while (!glfwWindowShouldClose(window)) {

				netManager.handleMessagesAndConnections();
				stateManager.updateState();
				gui.update();
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear
																	// frame
				// buffer
				stateManager.renderState();
				glfwSwapBuffers(window); // swap the colour buffers
				glfwPollEvents(); // Poll for window events. The key callback
									// above
				// will only be invoked during this call.

				try {
					timer.waitForTick();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			// Run the rendering loop until the user presses esc or quits
			while (running) {
				netManager.handleMessagesAndConnections();
				stateManager.updateState();

				try {
					timer.waitForTick();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		cleanup();
	}

	/**
	 * Return whether the application is headless or not
	 * 
	 * @return
	 */
	public boolean isHeadless() {
		return isHeadless;
	}

	/**
	 * Get the position of the cursor
	 * 
	 * @param windowID
	 */
	public static void getCursorPos(long windowID) {
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(windowID, xBuffer, yBuffer);
		double x = xBuffer.get(0);
		double y = yBuffer.get(0);

//		System.out.print(x + " : ");
//		System.out.println(y);

	}

	/**
	 * Get the game window that is being used
	 * 
	 * @return
	 */
	public long getWindow() {
		// Needed in order to have the same window over all states
		return window;
	}

	/**
	 * Get the state manager
	 * 
	 * @return
	 */
	public StateManager getStateManager() {
		return stateManager;
	}

	/**
	 * Get the input manager
	 * 
	 * @return
	 */
	public InputManager getInputManager() {
		return inputManager;
	}

	/**
	 * Get the asset manager
	 * 
	 * @return
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	public Timer getTimer()
	{
		return timer;
	}
	
	/**
	 * Get the sound manager
	 * 
	 * @return
	 */
	public SoundManager getSoundManager() {
		return soundManager;
	}

	/**
	 * Get the gui
	 * 
	 * @return
	 */
	public GUI getGui() {
		return gui;
	}

	/**
	 * Get the network manager
	 * 
	 * @return
	 */
	public NetworkManager getNetworkManager() {
		return netManager;
	}

	/**
	 * SEt the resolution of the screen
	 * 
	 * @param width
	 * @param height
	 */
	public void setResolution(int width, int height) {
		s_WindowSize = new Vec2(width, height);
	}

	/**
	 * Set the games viewport
	 * 
	 * @param right
	 * @param top
	 */
	public void setViewport(float right, float top) {
		s_Viewport = new Vec2(right, top);
		gui.resize();
	}

	/**
	 * Resize the components of the game to fit with the new window size
	 * 
	 * @param window
	 * @param width
	 * @param height
	 */
	public void resize(long window, int width, int height) {
		if (!isFullScreen) {
			glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
				@Override
				public void invoke(long window, int width, int height) {
					setResolution(width, height);
				};
			});
		}
	}

	/**
	 * Quit the application
	 */
	public void exit() {
		if (!isHeadless) {
			glfwSetWindowShouldClose(window, true);
		} else {
			running = false;
		}
	}

	/**
	 * Cleanup by terminating GLFW and the window
	 */
	public void cleanup() {
		if (!isHeadless) {

			if (debugProc != null)
				debugProc.free();

			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
			// Terminate GLFW
			glfwTerminate();
			soundManager.clean();
		}
	}
}