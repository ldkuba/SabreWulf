package engine.graphics.materials;

import org.lwjgl.opengl.GL20;

import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.maths.Vec3;
import engine.maths.Vec4;

public class Material
{
	private Texture m_Texture;

	private Vec4 m_AmbientColor;
	private Vec4 m_DiffuseColor;
	private Vec4 m_SpecularColor;
	
	private float m_Reflectance;
	
	private boolean m_IsLit;
	
	public Material()
	{
		m_AmbientColor = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		m_DiffuseColor = new Vec4(0.7f, 0.7f, 0.7f, 1.0f);
		m_SpecularColor = new Vec4(0.5f, 0.5f, 0.5f, 1.0f);
		m_Reflectance = 1.0f;
		
		m_IsLit = false;
	}
	
	public Material(Vec4 ambient, Vec4 diffuse, Vec4 specular, float reflectance, boolean isLit)
	{
		m_AmbientColor = ambient;
		m_DiffuseColor = diffuse;
		m_SpecularColor = specular;
		m_Reflectance = reflectance;
		
		m_IsLit = isLit;
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
	
	public boolean getIsLit()
	{
		return m_IsLit;
	}
	
	public void setIsLit(boolean isLit)
	{
		m_IsLit = isLit;
	}
	
	public void setUniforms(ShaderProgram shader)
	{
		int ambientLoc = shader.getUniformLayout().getUniformLocation("material.ambient");
		GL20.glUniform4fv(ambientLoc, m_AmbientColor.elementsFlipped());
		
		int diffuseLoc = shader.getUniformLayout().getUniformLocation("material.diffuse");
		GL20.glUniform4fv(diffuseLoc, m_DiffuseColor.elementsFlipped());
		
		int specularLoc = shader.getUniformLayout().getUniformLocation("material.specular");
		GL20.glUniform4fv(specularLoc, m_SpecularColor.elementsFlipped());
		
		int hasTextureLoc = shader.getUniformLayout().getUniformLocation("material.hasTexture");
		GL20.glUniform1i(hasTextureLoc, (m_Texture == null) ? 0 : 1);
		
		int reflectanceLoc = shader.getUniformLayout().getUniformLocation("material.reflectance");
		GL20.glUniform1f(reflectanceLoc, m_Reflectance);
	}
}
