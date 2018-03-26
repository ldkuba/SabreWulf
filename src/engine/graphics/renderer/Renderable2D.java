package engine.graphics.renderer;

import engine.graphics.VertexAttribute;
import engine.graphics.VertexLayout;
import engine.graphics.VertexLayout.AttributeTypes;
import engine.graphics.texture.Texture;
import engine.maths.Vec2;
import engine.maths.Vec4;

/**
 * This object is used to represent 2D sprites and is used internally by the Renderer2D.java
 * @author nawro
 *
 */
public class Renderable2D
{
	private static final int VERTEX_COUNT = 4;
	private static final int INDEX_COUNT = 6;
	private static final int VERTEX_PRIMITIVE_ATTRIB_COUNT = 3 + 2 + 4;
	
	private static VertexLayout s_VertexLayout;
	private float m_Width, m_Height;
	
	private Vec2[] m_UVs;
	
	private Texture m_Texture;
	private Vec4 m_Color;
	
	private Vec4 m_OverlayColor;
	
	/**
	 * Creates a 2D sprite with a texture
	 * @param width - width of the sprite
	 * @param height - height of the sprite
	 * @param color - color of the sprite to be used if no texture is applied
	 * @param texture - texture for the sprite
	 */
	public Renderable2D(float width, float height, Vec4 color, Texture texture)
	{
		this(width, height, color);
		
		m_Texture = texture;
		
		m_OverlayColor = new Vec4(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	/**
	 * Creates a 2D sprite without a texture
	 * @param width - width of the sprite
	 * @param height - height of the sprite
	 * @param color - color of the sprite
	 */
	public Renderable2D(float width, float height, Vec4 color)
	{
		m_Color = color;
		m_Width = width;
		m_Height = height;
		
		m_UVs = new Vec2[4];
		m_UVs[0] = new Vec2(0.0f, 0.0f);
		m_UVs[1] = new Vec2(0.0f, 1.0f);
		m_UVs[2] = new Vec2(1.0f, 1.0f);
		m_UVs[3] = new Vec2(1.0f, 0.0f);
		
		m_OverlayColor = new Vec4(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	/**
	 * Gets the layout of vertices used by all sprites
	 * @return the layout of the vertices
	 */
	public static VertexLayout getVertexLayout()
	{
		if(s_VertexLayout == null)
		{
			s_VertexLayout = new VertexLayout();
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // position (x, y, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1); // UV (x, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 1, false), 1); // textureSlot
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1); // colour (r, g, b, a)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1); // overlay colour (r, g, b, a)
		}
		
		return s_VertexLayout;
	}
	
	/**
	 * Gets the width of the sprite
	 * @return width of the sprite
	 */
	public float getWidth()
	{
		return m_Width;
	}
	
	/**
	 * Gets the height of the sprite
	 * @return heigth of the sprite
	 */
	public float getHeight()
	{
		return m_Height;
	}
	
	/**
	 * Sets the width of the sprite
	 * @param width - width of the sprite
	 */
	public void setWidth(float width)
	{
		m_Width = width;
	}
	
	/**
	 * Sets the height of the sprite
	 * @param height - height of the sprite
	 */
	public void setHeight(float height)
	{
		m_Height = height;
	}
	
	/**
	 * Gets the color of the sprite
	 * @return color of the sprite
	 */
	public Vec4 getColor()
	{
		return m_Color;
	}
	
	/**
	 * Sets the color of the sprite
	 * @param newColor - color of the sprite
	 */
	public void setColor(Vec4 newColor)
	{
		m_Color = newColor;
	}
	
	/**
	 * Gets the UV coordinates of the vertices of this sprite
	 * @return the UV coordinates
	 */
	public Vec2[] getUVs()
	{
		return m_UVs;
	}
	
	/**
	 * Sets the UV coordinates for this sprite
	 * @param UVs -  the UV coordinates
	 */
	public void setUVs(Vec2[] UVs)
	{
		m_UVs = UVs;
	}
	
	/**
	 * Gets the texture used by this sprite
	 * @return the texture used by this sprite
	 */
	public Texture getTexture()
	{
		return m_Texture;
	}
	
	/**
	 * Sets the texture used by this sprite
	 * @param texture - texture to be used
	 */
	public void setTexture(Texture texture)
	{
		m_Texture = texture;
	}
	
	/**
	 * Sets the overlay color for this sprite
	 * @param overlayColor - overlay color for the sprite
	 */
	public void setOverlayColor(Vec4 overlayColor)
	{
		m_OverlayColor = overlayColor;
	}
	
	/**
	 * Gets the overlay color 
	 * @return the overlay color
	 */
	public Vec4 getOverlayColor()
	{
		return m_OverlayColor;
	}
}
