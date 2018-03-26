package engine.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import engine.application.Application;

/**
 * Input manager is used to manage the inputs from both keyboard and mouse
 * 
 * @author SabreWulf
 *
 */
public class InputManager {

	private KeyboardHandler keyboardHandler;
	private MouseButtonHandler mouseButtonHandler;
	private DoubleBuffer mouseXBuff;
	private DoubleBuffer mouseYBuff;
	private Application app;
	private long window;

	/**
	 * Create a new input manager for the given application and set up the
	 * keyboard and mouse handlers
	 * 
	 * @param app
	 */
	public InputManager(Application app) {
		this.app = app;
		this.window = app.getWindow();

		keyboardHandler = new KeyboardHandler();
		mouseButtonHandler = new MouseButtonHandler();

		GLFW.glfwSetKeyCallback(window, keyboardHandler);
		GLFW.glfwSetMouseButtonCallback(window, mouseButtonHandler);

		mouseXBuff = BufferUtils.createDoubleBuffer(1);
		mouseYBuff = BufferUtils.createDoubleBuffer(1);
	}

	/**
	 * Returns true if a given key is pressed
	 * 
	 * @param keycode
	 * @return
	 */
	public boolean isKeyPressed(int keycode) {
		if (GLFW.glfwGetKey(window, keycode) == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns true if a given mouse button is pressed
	 * 
	 * @param button
	 * @return
	 */
	public boolean isMouseButtonPressed(int button) {
		if (GLFW.glfwGetMouseButton(window, button) == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Get the x position of the mouse cursor
	 * 
	 * @return
	 */
	public double getMouseX() {
		GLFW.glfwGetCursorPos(window, mouseXBuff, mouseYBuff);
		return mouseXBuff.get(0);
	}

	/**
	 * Get the y position of the mouse cursor
	 * 
	 * @return
	 */
	public double getMouseY() {
		GLFW.glfwGetCursorPos(window, mouseXBuff, mouseYBuff);
		return mouseYBuff.get(0);
	}

	/**
	 * Add a keyboard listener to the keyboard handler
	 * 
	 * @param listener
	 */
	public void addKeyboardListener(KeyboardListener listener) {
		keyboardHandler.addListener(listener);
	}

	/**
	 * Remove a keyboard listener from the keyboard handler
	 * 
	 * @param listener
	 */
	public void removeKeyboardListener(KeyboardListener listener) {
		keyboardHandler.removeListener(listener);
	}

	/**
	 * Add a mouse listener to the mouse handler
	 * 
	 * @param listener
	 */
	public void addMouseListener(MouseListener listener) {
		mouseButtonHandler.addListener(listener);
	}

	/**
	 * Remove a mouse listener from the mouse handler
	 * 
	 * @param listener
	 */
	public void removeMouseListener(MouseListener listener) {
		mouseButtonHandler.removeListener(listener);
	}
}
