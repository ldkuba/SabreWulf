package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

/**
 * Creates and manages buttons that can be toggled between two states 
 * @author User
 *
 */
public class ToggleButton extends GuiComponent {
	private Texture selectedTexture;
	private Texture deselectedTexture;
	
	boolean toggled = false;
	
	/**
	 * Creates a new button at the given position, with the given dimensions and two textures
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param selected
	 * @param deselected
	 */
	public ToggleButton(float x, float y, float width, float height, Texture selected, Texture deselected) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = true;
		
		selectedTexture = selected;
		deselectedTexture = deselected;
		
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity = new Entity("button");
		entity.addComponent(new TransformComponent());
		entity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), deselected, worldWidth, worldHeight));
	}
	
	/**
	 * Resizes the buttons when the window size gets resized
	 */
	@Override
	public void resize() {
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity.getSprite().setWidth(worldWidth);
		entity.getSprite().setHeight(worldHeight);
	}
	
	/**
	 * Specifies the buttons behaviour on click - overridable for custom behaviour
	 * @param toggled
	 */
	public void onClick(boolean toggled) {
		//Implement Button bheaviou here
	}
	
	/**
	 * Specifies what happens when the button is pressed
	 */
	//internal
	@Override
	public void onPress(int button) {
	}
	
	/**
	 * Specifies what happens when the button is released
	 */
	//internal
	@Override
	public void onRelease(int button) {
		//toggle texture
		if(toggled) {
			entity.getSprite().setTexture(deselectedTexture);
			toggled = false;
		}else {
			entity.getSprite().setTexture(selectedTexture);
			toggled = true;
		}
		//callback
		onClick(toggled);
	}
	
	/**
	 * Specifies what happens when the button is pressed repeatedly
	 */
	//internal
	@Override
	public void onRepeat(int button) {
		
	}
	
	/**
	 * Specifies what happens when the button is deselected
	 */
	public void deselect() {
		entity.getSprite().setTexture(deselectedTexture);
		toggled = false;
	}
}
