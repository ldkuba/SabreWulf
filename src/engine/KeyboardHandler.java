package engine;

import java.util.List;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public abstract class KeyboardHandler extends GLFWKeyCallback {

	// keyboardListener

	public boolean[] keys = new boolean[65535];
	private List<Integer> currentlyPressed = new ArrayList<Integer>();

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
		doAction(action, key);
	}

	// boolean method that returns true if a given key is pressed.
	public boolean isPressed(int key) {
		return keys[key];
	}

	public void doAction(int action, int key) {
		switch (action) {
		case (GLFW_PRESS):
			// System.out.println(("key pressed"));
			currentlyPressed.add(key);
			break;
		case (GLFW_RELEASE):
			// System.out.println(("key released"));
			if (currentlyPressed.contains(key)) {
				currentlyPressed.remove(key);
			}
			break;
		case (GLFW_REPEAT):
			// System.out.println(("key press repeated"));
			break;
		}
	}

}
