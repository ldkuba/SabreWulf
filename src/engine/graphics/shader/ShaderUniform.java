package engine.graphics.shader;

import org.lwjgl.opengl.GL20;

import engine.graphics.shader.ShaderUniformLayout.ShaderUniformType;

public class ShaderUniform
{
	private int m_ID;
	private String m_Name;
	private ShaderUniformType m_Type;
	
	public ShaderUniform(String name)
	{
		m_Name = name;
		m_ID = -1;
	}
	
	public void locateUniform(int programID)
	{
		m_ID = GL20.glGetUniformLocation(programID, m_Name);
	}
	
	public void resetLocation()
	{
		m_ID = -1;
	}
	
	public int getLocation()
	{
		return m_ID;
	}
	
}
