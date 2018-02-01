package graphics.renderer;

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
	private final int VERTEX_COUNT = 4;
	private final int INDEX_COUNT = 6;
	private final int VERTEX_PRIMITIVE_ATTRIB_COUNT = 3 + 2 + 4;
	
	private static VertexLayout s_VertexLayout;
	private float m_Width, m_Height;
	
	private VertexArray m_VAO;
	private IndexBuffer m_IBO;
	
	private Texture m_Texture;
	private Vec4 m_Color;
	
	private ShaderProgram m_Shader;
	
	public Renderable2D(int width, int height, String imagePath)
	{
		m_VAO = new VertexArray();
		m_VAO.bind();
		
		m_Color = new Vec4(0.0f, 0.0f, 0.0f, 1.0f);
		
		float[] vertices = new float[] {
			width, 0.0f, 1.0f, 1.0f, 0.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ(),
			0.0f, 0.0f, 1.0f, 0.0f, 0.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ(),
			0.0f, height, 1.0f, 1.0f, 0.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ(),
			width, height, 1.0f, 1.0f, 1.0f, m_Color.getW(), m_Color.getX(), m_Color.getY(), m_Color.getZ()
		};
		
		ByteBuffer vertexBuffer = BufferUtils.createByteBuffer(s_VertexLayout.getVertexSizeInBytes() * 8);
		for(int i = 0; i < VERTEX_PRIMITIVE_ATTRIB_COUNT; i++)
		{
			vertexBuffer.putFloat(vertices[i]);
		}
		vertexBuffer.flip();
		
		VertexBuffer vbo = new VertexBuffer(s_VertexLayout, vertexBuffer, VERTEX_COUNT, VertexBufferUsage.STATIC);
		
		m_VAO.addVertexBuffer(vbo);
		
		int[] indices = new int[] {
			0, 1, 2,
			2, 3, 0
		};
		
		m_IBO = new IndexBuffer(indices, INDEX_COUNT);
		
		vbo.unbind();
		m_IBO.unbind();
		m_VAO.unbind();
		
		m_Shader = new ShaderProgram();
		m_Shader.loadShader("res/shaders/shader.txt");
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
	
	public void draw()
	{
		m_Shader.bind();
		
		m_VAO.bind();
		m_IBO.bind();
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 0, null);
		
	}
	
}
