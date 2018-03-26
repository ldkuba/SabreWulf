package engine.graphics.shader;

import java.util.ArrayList;

/**
 * Representation of the layout of uniforms in a shader
 * @author nawro
 *
 */
public class ShaderUniformLayout
{
	public enum ShaderUniformType
	{
		Int,
		Float,
		Vec3,
		Vec4,
		Mat4
	};
	
	private ArrayList<ShaderUniform> m_ShaderUniforms;
	private int m_Count;
	
	/**
	 * Creates an empty uniform layout
	 */
	public ShaderUniformLayout()
	{
		m_ShaderUniforms = new ArrayList<>();
		m_Count = 0;
	}
	
	/**
	 * Adds a specified number of uniforms to the layout
	 * @param uniform - the uniform to add
	 * @param count - the number of uniforms to add to the shader
	 */
	public void addShaderUniform(ShaderUniform uniform, int count)
	{
		m_Count += 1;
		
		for(int i = 0; i < count; i++)
			m_ShaderUniforms.add(uniform);
	}
	/**
	 * Locates the uniforms stored in this layout, in the given shader
	 * @param programID - the id of the shader program
	 */
	public void locateUniforms(int programID)
	{
		for(ShaderUniform uniform : m_ShaderUniforms)
		{
			uniform.locateUniform(programID);
		}
	}
	
	/**
	 * Gets the location of a uniform based on its index
	 * @param index - the index of the uniform
	 * @return the location of the uniform in the shader
	 */
	public int getUniformLocation(int index)
	{
		return m_ShaderUniforms.get(index).getLocation();
	}
	
	/**
	 * Gets the location of a uniform based on its name
	 * @param name - the name of the uniform
	 * @return the location of the uniform in the shader
	 */
	public int getUniformLocation(String name)
	{
		for(ShaderUniform uniform : m_ShaderUniforms)
		{
			if(uniform.getName().equals(name))
			{
				return uniform.getLocation();
			}
		}
		
		return -2;
	}
}
