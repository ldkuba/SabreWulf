package engine.gui.components;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.font.Font;
import engine.maths.Vec4;

/**
 * A text field is used to display text on the GUI
 * 
 * @author SabreWulf
 *
 */
public class TextField extends GuiComponent {

	private float size;
	private float spread;
	private int maxStringLength;
	private TextComponent textComponent;
	private SpriteComponent spriteComponent;

	/**
	 * Create a new text field at the given position, size, spread and colour
	 * 
	 * @param x
	 * @param y
	 * @param font
	 * @param size
	 * @param spread
	 * @param maxStringLength
	 * @param textColor
	 */
	public TextField(float x, float y, Font font, float size, float spread, int maxStringLength, Vec4 textColor) {
		this.x = x;
		this.y = y;
		this.width = size * spread * maxStringLength / 2.0f;
		this.height = size;
		this.size = size;
		this.spread = spread;
		this.enabled = true;
		this.maxStringLength = maxStringLength;
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));
		float worldWidth = ((width * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f)) + worldHeight) * 1.1f;

		entity = new Entity("sprite");
		entity.addComponent(new TransformComponent());
		spriteComponent = new SpriteComponent(new Vec4(0.8f, 0.8f, 0.8f, 0.7f), worldWidth, worldHeight);
		entity.addComponent(spriteComponent);
		textComponent = new TextComponent(font, worldHeight, spread, textColor);
		entity.addComponent(textComponent);
	}

	@Override
	public void resize() {
		float worldWidth = (width * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f));
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));

		entity.getSprite().setWidth(worldWidth);
		entity.getSprite().setHeight(worldHeight);

		textComponent.setSize(worldHeight);
	}

	/**
	 * Set the text fields text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		textComponent.setText(text);
	}

	/**
	 * Set the colour of the text
	 * 
	 * @param newColor
	 */
	public void setColor(Vec4 newColor) {
		textComponent.setColor(newColor);
	}

	@Override
	public void setFocused(boolean focused) {
		super.setFocused(focused);
		if (focused) {
			spriteComponent.setColor(new Vec4(0.6f, 0.6f, 0.6f, 0.7f));
		} else {
			spriteComponent.setColor(new Vec4(0.8f, 0.8f, 0.8f, 0.7f));
		}
	}

	/**
	 * Add an entered character to the text field
	 * 
	 * @param key
	 */
	public void addChar(int key) {
		String current = textComponent.getText();
		if (current.length() >= maxStringLength) {
			return;
		}
		if (key == 32 || key == 39 || (key >= 44 && key <= 57) || key == 59 || key == 61 || (key >= 65 && key <= 93)
				|| key == 96) {
			current += String.valueOf((char) (key));
		} else {
			return;
		}
		setText(current);
	}

	/**
	 * Remove a character from the text field
	 */
	public void removeChar() {
		String current = textComponent.getText();
		if (current.length() == 0) {
			return;
		}
		current = current.substring(0, current.length() - 1);

		setText(current);
	}

	/**
	 * Set what happens when a key is pressed when typing in the text field
	 */
	@Override
	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			if (key == GLFW.GLFW_KEY_BACKSPACE) {
				removeChar();
			} else {
				addChar(key);
			}
		}
	}

	/**
	 * Set the size of the text field
	 * 
	 * @param size
	 */
	public void setSize(float size) {
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));

		textComponent.setSize(worldHeight);
		this.width = spread * size * maxStringLength / 2.0f;
	}

	/**
	 * Set the spread of the text field
	 * 
	 * @param spread
	 */
	public void setSpread(float spread) {
		textComponent.setSpread(spread);
		this.width = spread * size * maxStringLength / 2.0f;
	}

	/**
	 * Get the text that is in the text field
	 * 
	 * @return
	 */
	public String getText() {
		return textComponent.getText();
	}
}
