package engine.state;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.input.KeyboardListener;
import engine.input.MouseListener;
import engine.scene.Scene;

/**
 * AbstractState is a class that is extended by all different game states It
 * implements KeyboardListener and MouseListener.
 */

public abstract class AbstractState implements KeyboardListener, MouseListener {

	protected Scene scene;
	private Application application;

	// FPS counter
	private int frame = 0;
	private float second = 0;

	public AbstractState(Application application) {
		this.application = application;
		scene = new Scene(0, application);
	}

	/**
	 * Initialise the state
	 */
	public void init() {
		scene.init();
		if (!application.isHeadless()) {
			application.getGui().init(scene);
		}
	}

	/**
	 * Draw the state
	 */
	public void render() {
		if (!application.isHeadless()) {
			scene.render();
		}
	}

	/**
	 * Update the state
	 */
	public void update() {
		if (!application.isHeadless()) {
			// FPS Counter
			if (GLFW.glfwGetTime() - second >= 1.0f) {
				second += 1.0f;
				//System.out.println("FPS: " + frame);
				frame = 0;
			}
			frame++;
		}

		scene.update();
	}

	/**
	 * Deactivate the state (opposite of initialise)
	 */
	public abstract void deactivate();

	/**
	 * Get the states scene
	 * 
	 * @return
	 */
	public Scene getScene() {
		return scene;
	}
}
