package engine.graphics.materials;

import org.lwjgl.opengl.GL20;

import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.maths.Vec3;
import engine.maths.Vec4;

/**
 * Represents materials for objects passed ot the renderer
 * @author nawro
 *
 */
public class Material {
	private Texture m_Texture;

	private Vec4 m_AmbientColor;
	private Vec4 m_DiffuseColor;
	private Vec4 m_SpecularColor;
	
	private float m_Reflectance;
	
	private boolean m_IsLit;
	
	/**
	 * Creates a material and sets default values for all parameters
	 */
	public Material() {
		m_AmbientColor = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		m_DiffuseColor = new Vec4(0.7f, 0.7f, 0.7f, 1.0f);
		m_SpecularColor = new Vec4(0.5f, 0.5f, 0.5f, 1.0f);
		m_Reflectance = 1.0f;
		
		m_IsLit = false;
	}
	
	/**
	 * Creates a material and assigns its parameters
	 * @param ambient - ambient color of the material
	 * @param diffuse - diffuse color of the material
	 * @param specular - speculat color of the material
	 * @param reflectance - specifies how reflective the material is
	 * @param isLit - marks if the material should se lighting
	 */
	public Material(Vec4 ambient, Vec4 diffuse, Vec4 specular, float reflectance, boolean isLit) {
		m_AmbientColor = ambient;
		m_DiffuseColor = diffuse;
		m_SpecularColor = specular;
		m_Reflectance = reflectance;
		
		m_IsLit = isLit;
	}

	/**
	 * Gets the texture used by this material
	 * @return texture used by this material
	 */
	public Texture getTexture() {
		return m_Texture;
	}

	/**
	 * Sets the texture to be used by this material
	 * @param texture - texture to be used
	 */
	public void setTexture(Texture texture) {
		this.m_Texture = texture;
	}

	/**
	 * Gets the ambient color of this material
	 * @return ambient color of this material
	 */
	public Vec4 getAmbientColor() {
		return m_AmbientColor;
	}

	/**
	 * Sets the ambient color for this material
	 * @param ambientColor - ambientColor 
	 */
	public void setAmbientColor(Vec4 ambientColor) {
		this.m_AmbientColor = ambientColor;
	}

	/**
	 * Gets the diffuse color for the material
	 * @return the diffuse color for the material
	 */
	public Vec4 getDiffuseColor() {
		return m_DiffuseColor;
	}

	/**
	 * Sets the diffuse color for the material
	 * @param diffuseColor - the diffuse color for the material
	 */
	public void setDiffuseColor(Vec4 diffuseColor) {
		this.m_DiffuseColor = diffuseColor;
	}

	/**
	 * Gets the specular color for the material
	 * @return the specular color for this material
	 */
	public Vec4 getSpecularColor() {
		return m_SpecularColor;
	}

	/**
	 * Sets the specular color for this material
	 * @param specularColor - specular color for this material
	 */
	public void setSpecularColor(Vec4 specularColor) {
		this.m_SpecularColor = specularColor;
	}
	
	/**
	 * Gets the reflectance for this material
	 * @return the reflectance for this material
	 */
	public float getReflectance() {
		return m_Reflectance;
	}

	/**
	 * Sets the reflectance for this material
	 * @param reflectance - reflectance of this material
	 */
	public void setReflectance(float reflectance) {
		this.m_Reflectance = reflectance;
	}	
	
	/**
	 * Is this material lit
	 * @return is this material lit
	 */
	public boolean getIsLit() {
		return m_IsLit;
	}
	
	/**
	 * Sets if this material should be lit
	 * @param isLit - should this material be lit
	 */
	public void setIsLit(boolean isLit) {
		m_IsLit = isLit;
	}
	
	/**
	 * Sets the material related uniforms in the given shader
	 * @param shader - the shader for which the uniforms are set
	 */
	public void setUniforms(ShaderProgram shader) {
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
