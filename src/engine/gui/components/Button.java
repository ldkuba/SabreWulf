package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

/**
 * Create and manage buttons on the GUI
 * 
 * @author SabreWulf
 *
 */
public class Button extends GuiComponent {

	private Texture pressedTexture;
	private Texture releasedTexture;

	/**
	 * Create a new button at the given positon, with the given dimensions and
	 * texture
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param pressed
	 * @param released
	 */
	public Button(float x, float y, float width, float height, Texture pressed, Texture released) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = true;

		pressedTexture = pressed;
		releasedTexture = released;

		float worldWidth = (width * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f));
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));

		entity = new Entity("button");
		entity.addComponent(new TransformComponent());
		entity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), released, worldWidth, worldHeight));
	}

	/**
	 * Specify the buttons behaviour on click - overridable for custom behaviour
	 */
	public void onClick() {
		// Implement Button bheaviou here
	}

	/**
	 * Resize the button when the window size gets resized
	 */
	@Override
	public void resize() {
		float worldWidth = (width * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f));
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));

		entity.getSprite().setWidth(worldWidth);
		entity.getSprite().setHeight(worldHeight);
	}

	/**
	 * Specify what happens when the button is pressed
	 */
	@Override
	public void onPress(int button) {
		/* change button Texture */
		entity.getSprite().setTexture(pressedTexture);
	}

	/**
	 * Specify what happens when the button is released
	 */
	@Override
	public void onRelease(int button) {
		/* change button Texture */
		entity.getSprite().setTexture(releasedTexture);
		/* callback */
		onClick();
	}

	@Override
	public void onRepeat(int button) {

	}
}
