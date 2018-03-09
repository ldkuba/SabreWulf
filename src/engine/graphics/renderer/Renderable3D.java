package engine.graphics.renderer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import engine.graphics.IndexBuffer;
import engine.graphics.VertexArray;
import engine.graphics.VertexAttribute;
import engine.graphics.VertexBuffer;
import engine.graphics.VertexBuffer.VertexBufferUsage;
import engine.graphics.VertexLayout;
import engine.graphics.VertexLayout.AttributeTypes;
import engine.graphics.camera.Camera;
import engine.graphics.materials.Material;
import engine.maths.Mat4;

public class Renderable3D
{
	private String m_Filename;
	
	private VertexArray m_VAO;
	private IndexBuffer m_IBO;
	
	private Material m_Material;
	
	public Renderable3D(float[] vertices, float[] uvs, float[] normals, int[] indicies, Material material, String filename)
	{
		m_VAO = new VertexArray();
		m_VAO.bind();
		
		VertexLayout vertexLayout = new VertexLayout();
		vertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // positions
		vertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1); // uvs
		vertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // normals
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(vertexLayout.getVertexSizeInBytes() * vertices.length);
		
		for(int v = 0, u = 0, n = 0; v < vertices.length; v += 3, u += 2, n += 3)
		{
			buffer.asFloatBuffer().put(vertices[v+0]);
			buffer.asFloatBuffer().put(vertices[v+1]);
			buffer.asFloatBuffer().put(vertices[v+2]);
			
			buffer.asFloatBuffer().put(uvs[u+0]);
			buffer.asFloatBuffer().put(uvs[u+1]);
			
			buffer.asFloatBuffer().put(normals[n+0]);
			buffer.asFloatBuffer().put(normals[n+1]);
			buffer.asFloatBuffer().put(normals[n+2]);
		}
		
		buffer.flip();
		
		VertexBuffer vbo = new VertexBuffer(vertexLayout, buffer, vertices.length, VertexBufferUsage.STATIC);
		vbo.bind();
		m_VAO.addVertexBuffer(vbo);
		
		m_IBO = new IndexBuffer(indicies);
	
		m_Material = material;
		m_Filename = filename;
		
		//clean
		m_IBO.unbind();
		vbo.unbind();
		m_VAO.unbind();
	}
	
	public void draw(Renderer3D renderer, Mat4 modelMatrix)
	{
		m_Material.getShader().bind();
		//Set Uniforms
		//Model matrix
		int modelLoc = m_Material.getShader().getUniformLayout().getUniformLocation(0);
		GL20.glUniformMatrix4fv(modelLoc, true, modelMatrix.getElements());
		
		//View matrix
		int viewLoc = m_Material.getShader().getUniformLayout().getUniformLocation(1);
		GL20.glUniformMatrix4fv(viewLoc, true, renderer.getCamera().getViewMatrix().getElements());
		
		//Projection matrix
		int projLoc = m_Material.getShader().getUniformLayout().getUniformLocation(2);
		GL20.glUniformMatrix4fv(projLoc, true, renderer.getCamera().getProjectionMatrix().getElements());	
		
		//Texture
		int loc = GL20.glGetUniformLocation(m_Material.getShader().getID(), "texture");
		GL20.glUniform1i(loc, 0);
		
		m_VAO.bind();
		m_IBO.bind();
		
		m_Material.getTexture().bind(0);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, m_IBO.getCount(), GL11.GL_UNSIGNED_INT, 0);

		m_Material.getTexture().unbind(0);
		
		m_IBO.unbind();
		m_VAO.unbind();
		
		m_Material.getTexture().unbind(0);
	}
	
	public String getFilename()
	{
		return m_Filename;
	}
}
