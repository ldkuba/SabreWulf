package engine.graphics.shader;

import java.util.ArrayList;

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
	
	public ShaderUniformLayout()
	{
		m_ShaderUniforms = new ArrayList<>();
		m_Count = 0;
	}
	
	public void addShaderUniform(ShaderUniform uniform, int count)
	{
		m_Count += 1;
		
		for(int i = 0; i < count; i++)
			m_ShaderUniforms.add(uniform);
	}
	
	public void locateUniforms(int programID)
	{
		for(ShaderUniform uniform : m_ShaderUniforms)
		{
			uniform.locateUniform(programID);
		}
	}
	
	public int getUniformLocation(int index)
	{
		return m_ShaderUniforms.get(index).getLocation();
	}
	
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
