package engine.input;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Set up a mouse button handler which is used to invoke a mouse listener when
 * necessary
 * 
 * @author SabreWulf
 *
 */
public class MouseButtonHandler extends GLFWMouseButtonCallback {

	private ArrayList<MouseListener> mouseListeners;

	/**
	 * Initialise a new mouse button handler
	 */
	public MouseButtonHandler() {
		mouseListeners = new ArrayList<>();
	}

	/**
	 * Invoke the mouse listener from the handler when a mouse button is pressed
	 * or released
	 */
	@Override
	public void invoke(long window, int button, int action, int mods) {
		try {
			for (MouseListener listener : mouseListeners) {
				listener.mouseAction(button, action);
			}
		} catch (ConcurrentModificationException e) {
			return;
		}
	}

	/**
	 * Add a new mouse listener
	 * 
	 * @param listener
	 */
	public void addListener(MouseListener listener) {
		mouseListeners.add(listener);
	}

	/**
	 * Remove an existing mouse listener
	 * 
	 * @param listener
	 */
	public void removeListener(MouseListener listener) {
		if (mouseListeners.contains(listener)) {
			mouseListeners.remove(listener);
		}
	}

}
