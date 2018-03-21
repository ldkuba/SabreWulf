package engine.input;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * Set up a keyboard handler which is used to invoke a keyboard listener when
 * necessary
 * 
 * @author SabreWulf
 *
 */
public class KeyboardHandler extends GLFWKeyCallback {

	private ArrayList<KeyboardListener> keyboardListeners;

	/**
	 * Set up a new keyboard handler
	 */
	public KeyboardHandler() {
		keyboardListeners = new ArrayList<>();
	}

	/**
	 * Invoke the keyboard listener from the handler when a key is pressed or
	 * released
	 */
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		try {
			for (KeyboardListener listener : keyboardListeners) {
				listener.keyAction(key, action);
			}
		} catch (ConcurrentModificationException e) {
			return;
		}
	}

	/**
	 * Add a new listener to the keyboard handler
	 * 
	 * @param listener
	 */
	public void addListener(KeyboardListener listener) {
		keyboardListeners.add(listener);
	}

	/**
	 * Remove a listener from the keyboard handler
	 * @param listener
	 */
	public void removeListener(KeyboardListener listener) {
		if (keyboardListeners.contains(listener)) {
			keyboardListeners.remove(listener);
		}
	}
}
