package engine.input;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardHandler extends GLFWKeyCallback{
	
	private ArrayList<KeyboardListener> keyboardListeners;
	
	public KeyboardHandler() {
		keyboardListeners = new ArrayList<>();
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		
		try {
			for(KeyboardListener listener : keyboardListeners) {
				listener.keyAction(key, action);
			}
		} catch (ConcurrentModificationException e)
		{
			return;
		}
	}
	
	public void addListener(KeyboardListener listener) {
		keyboardListeners.add(listener);
	}
	
	public void removeListener(KeyboardListener listener) {
		if(keyboardListeners.contains(listener)) {
			keyboardListeners.remove(listener);
		}
	}
}
