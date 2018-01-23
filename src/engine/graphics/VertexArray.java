package engine.graphics;

import java.util.ArrayList;

import org.lwjgl.opengl.GL30;

public class VertexArray
{
	private int m_ID;
	
	private VertexBuffer m_VertexBuffer;
	
	public VertexArray()
	{
		m_ID = GL30.glGenVertexArrays();
	}

	//THE VAO MUST BE BOUND WHEN CALLING THIS FUNCTION
	public void addVertexBuffer(VertexBuffer buffer)
	{
		m_VertexBuffer = buffer;
	}
	
	public void bind()
	{
		GL30.glBindVertexArray(m_ID);
	}
	
	public void unbind()
	{
		GL30.glBindVertexArray(0);
	}
	
	public void close()
	{
		m_VertexBuffer.close();
		
		GL30.glDeleteVertexArrays(m_ID);
	}
}
