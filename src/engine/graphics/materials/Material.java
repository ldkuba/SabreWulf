package engine.graphics.materials;

import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.maths.Vec3;
import engine.maths.Vec4;

public class Material
{
	private ShaderProgram m_Shader;
	private Texture m_Texture;

	private Vec4 m_AmbientColor;
	private Vec4 m_DiffuseColor;
	private Vec4 m_SpecularColor;
	
	private float m_Reflectance;
	
	public Material(Vec4 ambient, Vec4 diffuse, Vec4 specular, float reflectance, ShaderProgram shader)
	{
		m_AmbientColor = ambient;
		m_DiffuseColor = diffuse;
		m_SpecularColor = specular;
		m_Reflectance = reflectance;
		
		m_Shader = shader;
	}

	public ShaderProgram getShader()
	{
		return m_Shader;
	}

	public void setShader(ShaderProgram m_Shader)
	{
		this.m_Shader = m_Shader;
	}

	public Texture getTexture()
	{
		return m_Texture;
	}

	public void setTexture(Texture m_Texture)
	{
		this.m_Texture = m_Texture;
	}

	public Vec4 getAmbientColor()
	{
		return m_AmbientColor;
	}

	public void setAmbientColor(Vec4 m_AmbientColor)
	{
		this.m_AmbientColor = m_AmbientColor;
	}

	public Vec4 getDiffuseColor()
	{
		return m_DiffuseColor;
	}

	public void setDiffuseColor(Vec4 m_DiffuseColor)
	{
		this.m_DiffuseColor = m_DiffuseColor;
	}

	public Vec4 getSpecularColor()
	{
		return m_SpecularColor;
	}

	public void setSpecularColor(Vec4 m_SpecularColor)
	{
		this.m_SpecularColor = m_SpecularColor;
	}

	public float getReflectance()
	{
		return m_Reflectance;
	}

	public void setReflectance(float m_Reflectance)
	{
		this.m_Reflectance = m_Reflectance;
	}	
}
