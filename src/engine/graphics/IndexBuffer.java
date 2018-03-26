package engine.graphics;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

/**
 * Stores indices of verticies for rendering
 * @author nawro
 *
 */
public class IndexBuffer
{
	private int m_ID;
	private int m_Count;
	
	/**
	 * Creates an empty index buffer
	 */
	public IndexBuffer()
	{
		m_Count = 0;
		m_ID = GL15.glGenBuffers();
	}
	
	/**
	 * Creates an index buffer and fills it with data
	 * @param data - information to fill the buffer with
	 */
	public IndexBuffer(int[] data)
	{
		m_Count = data.length;

		m_ID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_ID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Updates the index buffer with data
	 * @param data - information about indices
	 * @param count - number of indices
	 */
	public void updateData(IntBuffer data, int count)
	{
		m_Count = count;

		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
	}
	
	/**
	 * Binds the index buffer
	 */
	public void bind()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, m_ID);
	}
	
	/**
	 * Unbinds the index buffer
	 */
	public void unbind()
	{
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	/**
	 *
	 * @return number of indices
	 */
	public int getCount()
	{
		return m_Count;
	}
	
	/**
	 * Deletes the buffer
	 */
	public void close()
	{
		GL15.glDeleteBuffers(m_ID);
	}
}