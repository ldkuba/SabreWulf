package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.font.Font;
import engine.maths.Vec3;
import engine.maths.Vec4;

/**
 * Create and manage buttons on the GUI
 * 
 * @author SabreWulf
 *
 */
public class Label extends GuiComponent {	
	private float size;
	private float spread;
	private TextComponent textComponent;
	
	/** 
	 * Creates a new label at the given position, with the given dimensions and colour
	 * @param x
	 * @param y
	 * @param font
	 * @param size
	 * @param spread
	 * @param color
	 */
	public Label(float x, float y, Font font, float size, float spread, Vec4 color) {
		this.x = x;
		this.y = y;
		this.width = size*spread;
		this.height = size;
		this.size = size;
		this.spread = spread;
		this.enabled = true;
		
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity = new Entity("sprite");
		entity.addComponent(new TransformComponent());
		textComponent = new TextComponent(font, worldHeight, spread, color);
		entity.addComponent(textComponent);
	}
	
	/**
	 * Resizes the label when the window size gets resized
	 */
	@Override
	public void resize() {
		setSize(size);
	}
	
	/**
	 * Sets the colour of the label
	 * @param newColor
	 */
	public void setColor(Vec4 newColor) {
		textComponent.setColor(newColor);
	}
	
	/**
	 * Sets the text of the label
	 * @param text
	 */
	public void setText(String text) {
		textComponent.setText(text);
		this.width = spread * size * text.length();
	}
	
	/**
	 * Sets the size of the label
	 * @param size
	 */
	public void setSize(float size) {
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		textComponent.setSize(worldHeight);
		this.width = spread * size * textComponent.getText().length();
	}
	
	/**
	 * Sets the spread of the label
	 * @param spread
	 */
	public void setSpread(float spread) {
		textComponent.setSpread(spread);
		this.width = spread * size * textComponent.getText().length();
	}
	
	/**
	 * Sets the position of the label on the map
	 * Used only for the labels indicating the statuses of the actors
	 * @param newX
	 * @param newY
	 */
	public void setPosition(float newX, float newY) {
		
		this.x = newX;
		this.y = newY;
		
		setSpread(this.spread / Application.s_Viewport.getY());
		setSize(this.size / Application.s_Viewport.getX());
	}
}
