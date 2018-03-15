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
import engine.graphics.materials.Material;
import engine.graphics.shader.ShaderProgram;
import engine.maths.Mat4;

public class Renderable3D
{
	private String m_Filename;
	
	private VertexArray m_VAO;
	private IndexBuffer m_IBO;
	
	private Material m_Material;
	private ShaderProgram m_Shader;	public Renderable3D(float[] vertices, float[] uvs, float[] normals, int[] indicies, ShaderProgram shader, String filename)
	{
		m_Shader = shader;
		
		m_VAO = new VertexArray();
		m_VAO.bind();
		
		VertexLayout vertexLayout = new VertexLayout();
		vertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // positions
		vertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1); // uvs
		vertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1); // normals
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(vertexLayout.getVertexSizeInBytes() * (vertices.length/3));
		
		for(int v = 0, u = 0, n = 0; v < vertices.length; v += 3, u += 2, n += 3)
		{			
			buffer.putFloat(vertices[v+0]);
			buffer.putFloat(vertices[v+1]);
			buffer.putFloat(vertices[v+2]);
			
			buffer.putFloat(uvs[u+0]);
			buffer.putFloat(uvs[u+1]);
			
			buffer.putFloat(normals[n+0]);
			buffer.putFloat(normals[n+1]);
			buffer.putFloat(normals[n+2]);
		}
		
//		buffer.rewind();
//		
//		float[] test = new float[48];
//		buffer.asFloatBuffer().get(test);
		
		buffer.flip();
		
		VertexBuffer vbo = new VertexBuffer(vertexLayout, buffer, vertices.length/3, VertexBufferUsage.STATIC);
		vbo.bind();
		m_VAO.addVertexBuffer(vbo);
		
		m_IBO = new IndexBuffer(indicies);

		m_Filename = filename;
		
		//clean
		m_IBO.unbind();
		vbo.unbind();
		m_VAO.unbind();
	}
	
	public void setMaterial(Material material)
	{
		m_Material = material;
	}
	
	public Material getMaterial()
	{
		return m_Material;
	}
	
	public void draw(Renderer3D renderer, Mat4 modelMatrix)
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		m_Shader.bind();
		//Set Uniforms
		//Model matrix
		int modelLoc = m_Shader.getUniformLayout().getUniformLocation("modelMatrix");
		GL20.glUniformMatrix4fv(modelLoc, true, modelMatrix.getElements());
		
		//View matrix
		int viewLoc = m_Shader.getUniformLayout().getUniformLocation("viewMatrix");
		GL20.glUniformMatrix4fv(viewLoc, true, renderer.getCamera().getViewMatrix().getElements());
		
		//Projection matrix
		int projLoc = m_Shader.getUniformLayout().getUniformLocation("projectionMatrix");
		GL20.glUniformMatrix4fv(projLoc, true, renderer.getCamera().getProjectionMatrix().getElements());	
		
		//Ambient Light
		int ambientLoc = m_Shader.getUniformLayout().getUniformLocation("ambientLight");
		GL20.glUniform3fv(ambientLoc, renderer.getAmbientLight().elementsFlipped());
		
		//Material
		m_Material.setUniforms(m_Shader);
		
		int cameraPosLoc = m_Shader.getUniformLayout().getUniformLocation("camera_pos");
		GL20.glUniform3fv(cameraPosLoc, renderer.getCamera().getPosition().elementsFlipped());
		
		//Texture
		int loc = GL20.glGetUniformLocation(m_Shader.getID(), "texture_sampler");
		GL20.glUniform1i(loc, 0);
		
		m_VAO.bind();
		m_VAO.getVertexBuffer().bind();
		m_IBO.bind();
		
		if(m_Material.getTexture() != null)
			m_Material.getTexture().bind(0);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, m_IBO.getCount(), GL11.GL_UNSIGNED_INT, 0);

		if(m_Material.getTexture() != null)
			m_Material.getTexture().unbind(0);
		
		m_IBO.unbind();
		m_VAO.getVertexBuffer().unbind();
		m_VAO.unbind();
		
		m_Shader.unbind();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	public String getFilename()
	{
		return m_Filename;
	}
}
