package engine.graphics.renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import engine.graphics.IndexBuffer;
import engine.graphics.VertexArray;
import engine.graphics.VertexBuffer;
import engine.graphics.VertexBuffer.VertexBufferUsage;
import engine.graphics.camera.Camera;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.maths.Mat4;
import engine.maths.Vec4;

public class Renderer2D extends Renderer
{
	private final int MAX_SPRITES = 1000;
	private final int MAX_VERTS = MAX_SPRITES * 4;
	private final int MAX_INDEX = MAX_SPRITES * 6;

	private VertexArray m_VertexArray;
	private VertexBuffer m_VertexBuffer;
	private IndexBuffer m_IndexBuffer;

	private ByteBuffer m_VertexData;
	private IntBuffer m_IndexData;

	private ArrayList<Texture> m_Textures;

	private int m_SpriteCount;

	private ShaderProgram m_Shader;

	public Renderer2D()
	{
		m_VertexArray = new VertexArray();
		m_VertexBuffer = new VertexBuffer();
		m_IndexBuffer = new IndexBuffer();

		m_Shader = new ShaderProgram();
		m_Shader.loadShader("res/shaders/shader.txt"); // Later proper shader
	}

	// called every frame before submitting sprites
	public void init(Camera camera)
	{
		m_VertexData = BufferUtils.createByteBuffer(MAX_VERTS);
		m_IndexData = BufferUtils.createIntBuffer(MAX_INDEX);

		m_SpriteCount = 0;

		m_Shader.bind();

		// Set Shader uniforms
		
		
	}

	public void submit(Renderable2D renderable, Mat4 transformation)
	{
		float width = renderable.getWidth();
		float height = renderable.getHeight();
		Vec4 color = renderable.getColor();
		
		Vec4 v1 = new Vec4(0.0f, 0.0f, 1.0f, 1.0f);
		Vec4 v2 = new Vec4(width, 0.0f, 1.0f, 1.0f);
		Vec4 v3 = new Vec4(width, height, 1.0f, 1.0f);
		Vec4 v4 = new Vec4(0.0f, height, 1.0f, 1.0f);
		
		v1 = v1.mult(transformation);
		v2 = v2.mult(transformation);
		v3 = v3.mult(transformation);
		v4 = v4.mult(transformation);
		
		int textureSlot = -1;
		
		for(int i = 0; i < m_Textures.size(); i++)
		{
			if(m_Textures.get(i).getID() == renderable.getTexture().getID())
			{
				textureSlot = i;
				break;
			}
		}
		
		if(textureSlot == -1)
		{
			m_Textures.add(renderable.getTexture());
		}
		
		float[] vertices = new float[]
		{ 
				v1.getW(), v1.getX(), v1.getY(), 0.0f, 0.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ(),
				v2.getW(), v2.getX(), v2.getY(), 1.0f, 0.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ(),
				v3.getW(), v3.getX(), v3.getY(), 1.0f, 1.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ(),
				v4.getW(), v4.getX(), v4.getY(), 0.0f, 1.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ() 
		};

		m_IndexData.put(0 + 4 * m_SpriteCount);
		m_IndexData.put(1 + 4 * m_SpriteCount);
		m_IndexData.put(2 + 4 * m_SpriteCount);

		m_IndexData.put(2 + 4 * m_SpriteCount);
		m_IndexData.put(3 + 4 * m_SpriteCount);
		m_IndexData.put(0 + 4 * m_SpriteCount);
		
		

		m_SpriteCount++;
	}

	public void drawAll()
	{
		m_VertexData.flip();
		m_IndexData.flip();

		m_VertexArray.bind();
		m_VertexBuffer.bind();
		m_VertexBuffer.updateData(Renderable2D.getVertexLayout(), m_VertexData, m_SpriteCount * 4, VertexBufferUsage.DYNAMIC, true);

		m_IndexBuffer.bind();
		m_IndexBuffer.updateData(m_IndexData, m_SpriteCount * 6);		
		
		
	}
}
