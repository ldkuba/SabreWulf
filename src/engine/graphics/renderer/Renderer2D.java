package engine.graphics.renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import engine.graphics.IndexBuffer;
import engine.graphics.VertexArray;
import engine.graphics.VertexBuffer;
import engine.graphics.VertexBuffer.VertexBufferUsage;
import engine.graphics.camera.Camera;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.shader.ShaderUniform;
import engine.graphics.texture.Texture;
import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;

public class Renderer2D extends Renderer
{
	private final int MAX_SPRITES = 10000;
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
		
		m_VertexData = BufferUtils.createByteBuffer(0);
		m_IndexData = BufferUtils.createIntBuffer(0);
		
		m_Textures = new ArrayList<>();
		
		m_Shader = new ShaderProgram();
		m_Shader.loadShader("res/shaders/shader.txt"); // Later proper shader
		
		m_Shader.bind();
		
		m_Shader.getUniformLayout().addShaderUniform(new ShaderUniform("modelMatrix"), 1);
		m_Shader.getUniformLayout().addShaderUniform(new ShaderUniform("viewMatrix"), 1);
		m_Shader.getUniformLayout().addShaderUniform(new ShaderUniform("projectionMatrix"), 1);
		//m_Shader.getUniformLayout().addShaderUniform(new ShaderUniform("myMatrix"), 1);
		m_Shader.locateUniforms();
		
		m_Shader.unbind();
	}

	// called every frame before submitting sprites
	public void init(Camera camera)
	{
		m_VertexData = BufferUtils.createByteBuffer(MAX_VERTS*Renderable2D.getVertexLayout().getVertexSizeInBytes());
		m_IndexData = BufferUtils.createIntBuffer(MAX_INDEX);

		m_Textures.clear();
		
		m_SpriteCount = 0;

		m_Shader.bind();
		
		// Set Shader uniforms
		//Model matrix
		int modelLoc = m_Shader.getUniformLayout().getUniformLocation(0);
		GL20.glUniformMatrix4fv(modelLoc, true, Mat4.identity().getElements());
		
		//View matrix
		int viewLoc = m_Shader.getUniformLayout().getUniformLocation(1);
		GL20.glUniformMatrix4fv(viewLoc, true, camera.getViewMatrix().getElements());
		
		//Projection matrix
		int projLoc = m_Shader.getUniformLayout().getUniformLocation(2);
		GL20.glUniformMatrix4fv(projLoc, true, camera.getProjectionMatrix().getElements());	
		
		m_Shader.unbind();	
	}

	public void submit(Renderable2D renderable, Mat4 transformation)
	{
		float width = renderable.getWidth();
		float height = renderable.getHeight();
		Vec4 color = renderable.getColor();
		
		Vec4 v1 = new Vec4(-width/2.0f, height/2.0f, 1.0f, 1.0f);
		Vec4 v2 = new Vec4(-width/2.0f, -height/2.0f, 1.0f, 1.0f);
		Vec4 v3 = new Vec4(width/2.0f, -height/2.0f, 1.0f, 1.0f);
		Vec4 v4 = new Vec4(width/2.0f, height/2.0f, 1.0f, 1.0f);
		
		v1 = v1.mult(transformation);
		v2 = v2.mult(transformation);
		v3 = v3.mult(transformation);
		v4 = v4.mult(transformation);
		
		float textureSlot = -1;
		
		if(renderable.getTexture() != null)
		{
			for(int i = 0; i < m_Textures.size(); i++)
			{
				if(m_Textures.get(i).getID() == renderable.getTexture().getID())
				{
					textureSlot = i;
					break;
				}
			}
			
			if(textureSlot == -1.0f)
			{
				textureSlot = m_Textures.size();
				m_Textures.add(renderable.getTexture());		
			}
		}
		
		float[] vertices = new float[]
		{ 
				v1.getW(), v1.getX(), v1.getY(), 0.0f, 0.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ(),
				v2.getW(), v2.getX(), v2.getY(), 0.0f, 1.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ(),
				v3.getW(), v3.getX(), v3.getY(), 1.0f, 1.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ(),
				v4.getW(), v4.getX(), v4.getY(), 1.0f, 0.0f, textureSlot, color.getW(), color.getX(), color.getY(), color.getZ() 
		};

		m_IndexData.put(0 + 4 * m_SpriteCount);
		m_IndexData.put(1 + 4 * m_SpriteCount);
		m_IndexData.put(2 + 4 * m_SpriteCount);

		m_IndexData.put(2 + 4 * m_SpriteCount);
		m_IndexData.put(3 + 4 * m_SpriteCount);
		m_IndexData.put(0 + 4 * m_SpriteCount);
		
		for(int i = 0; i < vertices.length; i++)
		{
			m_VertexData.putFloat(vertices[i]);
		}

		m_SpriteCount++;
	}

	public void drawAll()
	{				
		m_Shader.bind();
		
		m_VertexData.flip();
		m_IndexData.flip();
		m_VertexArray.bind();
		m_VertexBuffer.bind();
		m_VertexBuffer.updateData(Renderable2D.getVertexLayout(), m_VertexData, m_SpriteCount * 4, VertexBufferUsage.DYNAMIC, true);

		m_IndexBuffer.bind();
		m_IndexBuffer.updateData(m_IndexData, m_SpriteCount * 6);	
		
		//bind textures
		for(int i = 0; i < m_Textures.size(); i++)
			m_Textures.get(i).bind(i);
		
		//set texture units
		for(int i = 0; i < m_Textures.size(); i++)
		{
			int loc = GL20.glGetUniformLocation(m_Shader.getID(), "texture[" + i + "]");
			GL20.glUniform1i(loc, i);
		}
		
		while(GL11.glGetError() != GL11.GL_NO_ERROR)
		{}
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, m_SpriteCount * 6, GL11.GL_UNSIGNED_INT, 0);
		//System.out.println(GL11.glGetError());
		
		for(int i = 0; i < m_Textures.size(); i++)
			m_Textures.get(i).unbind(i);
		
		m_IndexBuffer.unbind();
		m_VertexBuffer.unbind();
		m_VertexArray.unbind();
		
		m_Shader.unbind();
	}
}
