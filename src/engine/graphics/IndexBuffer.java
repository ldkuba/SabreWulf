package engine.graphics;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

public class IndexBuffer
{
	private int m_ID;
	private int m_Count;
	
	public IndexBuffer()
	{
		m_Count = 0;
		m_ID = GL15.glGenBuffers();
	}
	
	public IndexBuffer(int[] data)
	{
		m_Count = data.length;
		
		m_ID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_ID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
	}
	
	public void updateData(IntBuffer data, int count)
	{
		m_Count = count;
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
	}
	
	public void bind()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_ID);
	}
	
	public void unbind()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public int getCount()
	{
		return m_Count;
	}
	
	public void close()
	{
		GL15.glDeleteBuffers(m_ID);
	}
}