package engine.graphics.shader;

import org.lwjgl.opengl.GL20;

import engine.graphics.shader.ShaderUniformLayout.ShaderUniformType;

/**
 * Representation of a shader uniform
 * @author nawro
 *
 */
public class ShaderUniform
{
	private int m_ID;
	private String m_Name;
	private ShaderUniformType m_Type;
	
	/**
	 * Creates a shader uniform with the given name
	 * @param name - name of the uniform
	 */
	public ShaderUniform(String name)
	{
		m_Name = name;
		m_ID = -1;
	}
	
	/**
	 * Sets the location of the uniform in the shader
	 * @param programID - he id of the shader program
	 */
	public void locateUniform(int programID)
	{
		m_ID = GL20.glGetUniformLocation(programID, m_Name);
	}
	
	/**
	 * Sets the location of the uniform to -1 (unassigned)
	 */
	public void resetLocation()
	{
		m_ID = -1;
	}
	
	/**
	 * Gets the location of the uniform
	 * @return the location of the uniform
	 */
	public int getLocation()
	{
		return m_ID;
	}
	
	/**
	 * Gets the name of the uniform
	 * @return name of the uniform
	 */
	public String getName()
	{
		return m_Name;
	}
}
