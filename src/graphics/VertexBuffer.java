package graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;

public class VertexBuffer
{
	private int m_ID;
	
	private int m_Count;
	
	private VertexBufferUsage m_Usage;
	
	public enum VertexBufferUsage
	{
		STATIC, DYNAMIC
	};
	
	public VertexBuffer()
	{
		m_ID = GL15.glGenBuffers();
	}
	
	public VertexBuffer(ByteBuffer data, int count, VertexBufferUsage usage)
	{
		m_ID = GL15.glGenBuffers();
		
		updateData(data, count, usage);
	}
	
	public void updateData(ByteBuffer data, int count, VertexBufferUsage usage)
	{
		m_Count = count;
		m_Usage = usage;
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_ID);
		if(m_Usage.equals(VertexBufferUsage.STATIC))
		{
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		}else
		{
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
		}
	}
	
	public void Bind()
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_ID);
	}
	
	public void Unbind()
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void close()
	{
		GL15.glDeleteBuffers(m_ID);
	}
}