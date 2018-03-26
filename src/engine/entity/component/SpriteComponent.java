package engine.entity.component;

import engine.graphics.renderer.Renderable2D;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.texture.Texture;
import engine.maths.Mat4;
import engine.maths.Vec2;
import engine.maths.Vec4;

/**
 * Used to create a component for any sprite that will be used in the GUI
 * @author SabreWulf
 *
 */
public class SpriteComponent extends AbstractComponent
{
	private Renderable2D m_Sprite;
	
	/**
	 * Creates a sprite component with its texture
	 * @param color
	 * @param texture
	 * @param width
	 * @param height
	 */
	public SpriteComponent(Vec4 color, Texture texture, float width, float height)
	{
		m_Sprite = new Renderable2D(width, height, color, texture);
	}
	
	/**
	 * Creates a sprite component
	 * @param color
	 * @param width
	 * @param height
	 */
	public SpriteComponent(Vec4 color, float width, float height)
	{
		m_Sprite = new Renderable2D(width, height, color);
	}
	
	/**
	 * Sets the texture of the sprite
	 * @param texture
	 */
	public void setTexture(Texture texture)
	{
		m_Sprite.setTexture(texture);
	}
	
	/**
	 * Sets the colour of the sprite
	 * @param newColor
	 */
	public void setColor(Vec4 newColor)
	{
		m_Sprite.setColor(newColor);
	}
	
	/**
	 * Renders the sprite
	 * @param renderer
	 * @param transformation
	 */
	public void submit(Renderer2D renderer, Mat4 transformation)
	{
		renderer.submit(m_Sprite, transformation);
	}
	
	/**
	 * Gets the height of the sprite
	 * @return
	 */
	public float getHeight(){
		return m_Sprite.getHeight();
	}
	
	/**
	 * Gets the width of the sprite
	 * @return
	 */
	public float getWidth(){
		return m_Sprite.getWidth();
	}
	
	/** 
	 * Sets the UV coordinates of the sprite
	 * @param UVs
	 */
	public void setUVs(Vec2[] UVs)
	{
		m_Sprite.setUVs(UVs);
	}
	
	/**
	 * Sets the width of the sprite
	 * @param width
	 */
	public void setWidth(float width)
	{
		m_Sprite.setWidth(width);
	}
	
	/**
	 * Sets the height of the sprite
	 * @param height
	 */
	public void setHeight(float height)
	{
		m_Sprite.setHeight(height);
	}
	
	/**
	 * Sets the overlay colour of the sprite
	 * @param overlayColor
	 */
	public void setOverlayColor(Vec4 overlayColor)
	{
		m_Sprite.setOverlayColor(overlayColor);
	}

	/**
	 * Updates the sprite
	 */
	@Override
	public void update()
	{
	}
}

