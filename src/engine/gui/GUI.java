package engine.gui;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.component.TransformComponent;
import engine.gui.components.GuiComponent;
import engine.input.KeyboardListener;
import engine.input.MouseListener;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;

/**
 * Create and manage a graphical user interface for the game
 * 
 * @author SabreWulf
 *
 */

public class GUI implements MouseListener, KeyboardListener {

	private Scene scene;
	private ArrayList<GuiComponent> components;
	private Application app;

	/**
	 * Create a new GUI with an input manager
	 * 
	 * @param app
	 */
	public GUI(Application app) {
		this.app = app;
		components = new ArrayList<>();
		app.getInputManager().addMouseListener(this);
		app.getInputManager().addKeyboardListener(this);
	}

	/**
	 * Initialise the gui for the given scene
	 * 
	 * @param newScene
	 */
	public void init(Scene newScene) {
		if (this.scene != null) {
			for (GuiComponent component : components) {
				this.scene.removeEntity(component.getEntity());
			}
			components.clear();
		}
		this.scene = newScene;
	}

	/**
	 * Update the GUI
	 */
	public void update() {
		for (GuiComponent component : components) {
			TransformComponent transform = component.getEntity().getTransform();
			Vec2 viewPort = Application.s_Viewport;
			Vec2 windowSize = Application.s_WindowSize;
			Vec3 result = new Vec3(component.getX() * windowSize.getX() / 100.0f,
					component.getY() * windowSize.getY() / 100.0f, 0.0f);
			Vec3 corner = new Vec3((component.getWidth() * windowSize.getX() / 100.0f) / 2.0f,
					(component.getHeight() * windowSize.getY() / 100.0f) / 2.0f, 0.0f);
			corner = Vec3.add(corner, result);
			Vec3 centre = new Vec3(corner.getX() - windowSize.getX() / 2.0f, windowSize.getY() / 2.0f - corner.getY(),
					0.0f);

			float scaleX = viewPort.getX() / (windowSize.getX() / 2.0f);
			float scaleY = viewPort.getY() / (windowSize.getY() / 2.0f);

			Vec3 centreScaled = new Vec3(centre.getX() * scaleX, centre.getY() * scaleY, 0.0f);
			Vec3 camPos = new Vec3(scene.getCamera().getPosition());
			camPos.scale(1.0f);
			Vec3 finalPosition = Vec3.add(centreScaled, camPos);
			finalPosition.setZ(-1.0f);

			transform.setPosition(finalPosition);
		}
	}

	/**
	 * Add a new component to the GUI
	 * 
	 * @param component
	 */
	public void add(GuiComponent component) {
		components.add(component);
		scene.addEntity(component.getEntity());
	}

	/**
	 * Remove a component from the GUI
	 * 
	 * @param component
	 */
	public void remove(GuiComponent component) {
		components.remove(component);
		scene.removeEntity(component.getEntity());
	}

	/**
	 * Specify what happens when the mouse is clicked on the gui
	 */
	@Override
	public void mouseAction(int button, int action) {
		for (GuiComponent component : components) {
			if (app.getInputManager().getMouseX() >= component.getXAbsolute()
					&& app.getInputManager().getMouseX() <= component.getXAbsolute() + component.getWidthAbsolute()
					&& app.getInputManager().getMouseY() >= component.getYAbsolute()
					&& app.getInputManager().getMouseY() <= component.getYAbsolute() + component.getHeightAbsolute()) {
				if (action == GLFW.GLFW_PRESS) {
					if (component.isEnabled()) {
						component.setFocused(true);
						component.onPress(button);
					}
				} else if (action == GLFW.GLFW_RELEASE) {
					if (component.isEnabled()) {
						component.onRelease(button);
					}
				} else if (action == GLFW.GLFW_REPEAT) {
					if (component.isEnabled()) {
						component.onRepeat(button);
					}
				}
			} else {
				if (action == GLFW.GLFW_PRESS) {
					component.setFocused(false);
				}
			}
		}
	}

	/**
	 * Specify what happens when a key is pressed on the GUI
	 */
	@Override
	public void keyAction(int key, int action) {
		for (GuiComponent component : components) {
			if (component.isEnabled() && component.isFocused())
				component.onKeyPress(key, action);
		}
	}

	/**
	 * Resize the GUI when the game window is resized
	 */
	public void resize() {
		for (GuiComponent component : components) {
			component.resize();
		}
	}

	public ArrayList<GuiComponent> getComponents() {
		return components;
	}
}
