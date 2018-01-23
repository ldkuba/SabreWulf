package input;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardHandler extends GLFWKeyCallback{
	
	private ArrayList<KeyboardListener> keyboardListeners;
	
	public KeyboardHandler() {
		keyboardListeners = new ArrayList<>();
	}

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		for(KeyboardListener listener : keyboardListeners) {
			listener.keyAction(key, action);
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
