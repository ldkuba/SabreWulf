package engine.input;

import java.util.ArrayList;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonHandler extends GLFWMouseButtonCallback{

	private ArrayList<MouseListener> mouseListeners;
	
	public MouseButtonHandler() {
		mouseListeners = new ArrayList<>();
	}
	
	@Override
	public void invoke(long window, int button, int action, int mods){
		for(MouseListener listener : mouseListeners) {
			listener.mouseAction(button, action);
		}
	}
	
	public void addListener(MouseListener listener) {
		mouseListeners.add(listener);
	}
	
	public void removeListener(MouseListener listener) {
		if(mouseListeners.contains(listener)) {
			mouseListeners.remove(listener);
		}
	}

}
