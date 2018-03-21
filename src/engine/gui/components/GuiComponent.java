package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;

/**
 * GUI Component class is extended by any component that is used on the GUI
 * 
 * @author SabreWuld
 *
 */
public class GuiComponent {

	protected Entity entity;
	protected float x, y;
	protected float width;
	protected float height;
	protected boolean enabled;
	protected boolean focused;

	/**
	 * Specify what happens when a button is pressed
	 */
	public void onPress(int button) {

	}

	/**
	 * Specify what happens when a button is released
	 */
	public void onRelease(int button) {

	}

	public void onRepeat(int button) {

	}

	
	public void onKeyPress(int key, int action) {

	}

	/**
	 * Resize the component when the window size gets resized
	 */
	public void resize() {

	}

	/**
	 * Set whether or not the component has been enabled
	 * 
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Return whether or not the component is enabled
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set whether or not the component is focused
	 * 
	 * @param focused
	 */
	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	/**
	 * Get whether or not the component is focused
	 * 
	 * @return
	 */
	public boolean isFocused() {
		return focused;
	}

	/**
	 * Get the entity that is related to the component
	 * 
	 * @return
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Get the x position of the component
	 * 
	 * @return
	 */
	public float getX() {
		return x;
	}

	/**
	 * Get the absolute x position of the component
	 * 
	 * @return
	 */
	public float getXAbsolute() {
		return x * Application.s_WindowSize.getX() / 100.0f;
	}

	/**
	 * Set the x position of the component
	 * 
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Get the y position of the component
	 * 
	 * @return
	 */
	public float getY() {
		return y;
	}

	/**
	 * Get the absolute y position of the component
	 * 
	 * @return
	 */
	public float getYAbsolute() {
		return y * Application.s_WindowSize.getY() / 100.0f;
	}

	/**
	 * Set the y position of the component
	 * 
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Get the width of the component
	 * 
	 * @return
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Get the absolute width of the component
	 * 
	 * @return
	 */
	public float getWidthAbsolute() {
		return width * Application.s_WindowSize.getX() / 100.0f;
	}

	/**
	 * Set the width of the gui component
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Get the height of the gui component
	 * 
	 * @return
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Get the absolute height of the component
	 * 
	 * @return
	 */
	public float getHeightAbsolute() {
		return height * Application.s_WindowSize.getY() / 100.0f;
	}

	/**
	 * Set the height of the component
	 * 
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	};
}