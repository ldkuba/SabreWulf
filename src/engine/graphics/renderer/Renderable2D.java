package engine.graphics.renderer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import engine.graphics.IndexBuffer;
import engine.graphics.VertexArray;
import engine.graphics.VertexAttribute;
import engine.graphics.VertexBuffer;
import engine.graphics.VertexBuffer.VertexBufferUsage;
import engine.graphics.VertexLayout;
import engine.graphics.VertexLayout.AttributeTypes;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

public class Renderable2D
{
	private static final int VERTEX_COUNT = 4;
	private static final int INDEX_COUNT = 6;
	private static final int VERTEX_PRIMITIVE_ATTRIB_COUNT = 3 + 2 + 4;
	
	private static VertexLayout s_VertexLayout;
	private float m_Width, m_Height;
	
	private Texture m_Texture;
	private Vec4 m_Color;
	
	public Renderable2D(float width, float height, Vec4 color, Texture texture)
	{
		this(width, height, color);
		
		m_Texture = texture;
	}
	
	public Renderable2D(float width, float height, Vec4 color)
	{
		m_Color = color;
		m_Width = width;
		m_Height = height;
	}
	
	public static VertexLayout getVertexLayout()
	{
		if(s_VertexLayout == null)
		{
			s_VertexLayout = new VertexLayout();
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // position (x, y, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1); // UV (x, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 1, false), 1); // textureSlot
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1); // colour (r, g, b, a)
		}
		
		return s_VertexLayout;
	}
	
	public float getWidth()
	{
		return m_Width;
	}
	
	public float getHeight()
	{
		return m_Height;
	}
	
	public Vec4 getColor()
	{
		return m_Color;
	}
	
	public Texture getTexture()
	{
		return m_Texture;
	}
	
	public void setTexture(Texture texture)
	{
		m_Texture = texture;
	}
}
