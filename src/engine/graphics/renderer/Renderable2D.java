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
	
	private ByteBuffer m_VertexData;
	
	private Texture m_Texture;
	private Vec4 m_Color;
	
	public Renderable2D(int width, int height, Vec4 color, String imagePath)
	{
		this(width, height, color);
		
		m_Texture = new Texture(imagePath);
		
	}
	
	public Renderable2D(int width, int height, Vec4 color)
	{
		m_Color = color;
		
		float[] vertices = new float[] {
			width, 0.0f, 1.0f, 1.0f, 0.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ(),
			0.0f, 0.0f, 1.0f, 0.0f, 0.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ(),
			0.0f, height, 1.0f, 1.0f, 0.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ(),
			width, height, 1.0f, 1.0f, 1.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ()
		};
		
		m_VertexData = BufferUtils.createByteBuffer(s_VertexLayout.getVertexSizeInBytes() * 8);
		
		for(int i = 0; i < VERTEX_PRIMITIVE_ATTRIB_COUNT; i++)
		{
			m_VertexData.putFloat(vertices[i]);
		}
	}
	
	public static VertexLayout getVertexLayout()
	{
		if(s_VertexLayout == null)
		{
			s_VertexLayout = new VertexLayout();
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // position (x, y, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1); // UV (x, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1); // colour (r, g, b, a)
		}
		
		return s_VertexLayout;
	}

	public ByteBuffer getVertexData()
	{
		return m_VertexData;
	}	
}
