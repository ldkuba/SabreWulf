package graphics;

import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;

public class IndexBuffer
{
	private int m_ID;
	private int m_Count;
	
	public IndexBuffer(int[] data, int count)
	{
		m_Count = count;
		
		m_ID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_ID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
	}
	
	public void Bind()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_ID);
	}
	
	public void Unbind()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void close()
	{
		GL15.glDeleteBuffers(m_ID);
	}
}