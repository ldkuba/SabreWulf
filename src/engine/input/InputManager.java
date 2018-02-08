package engine.input;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import engine.application.Application;

public class InputManager {

	private KeyboardHandler keyboardHandler;
	private MouseButtonHandler mouseButtonHandler;

	private DoubleBuffer mouseXBuff;
	private DoubleBuffer mouseYBuff;

	private long window;
	private Application app;

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

	public boolean isKeyPressed(int keycode) {
		if (GLFW.glfwGetKey(window, keycode) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isMouseButtonPressed(int button) {
		if (GLFW.glfwGetMouseButton(window, button) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public double getMouseX() {
		GLFW.glfwGetCursorPos(window, mouseXBuff, mouseYBuff);
		return mouseXBuff.get(0);
	}

	public double getMouseY() {
		GLFW.glfwGetCursorPos(window, mouseXBuff, mouseYBuff);
		return mouseYBuff.get(0);
	}

	public void addKeyboardListener(KeyboardListener listener) {
		keyboardHandler.addListener(listener);
	}

	public void addMouseListener(MouseListener listener) {
		mouseButtonHandler.addListener(listener);
	}

	public void removeKeyboardListener(KeyboardListener listener) {
		keyboardHandler.removeListener(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		mouseButtonHandler.removeListener(listener);
	}
}
