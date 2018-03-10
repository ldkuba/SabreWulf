package engine.assets;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIString;
import org.lwjgl.assimp.Assimp;

import engine.font.Font;
import engine.graphics.materials.Material;
import engine.graphics.renderer.Renderable3D;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

public class AssetManager
{
	private ArrayList<Texture> m_Textures;
	private ArrayList<Font> m_Fonts;
	private ArrayList<Renderable3D[]> m_Meshes;
	
	public AssetManager()
	{	
		m_Textures = new ArrayList<>();
		m_Fonts = new ArrayList<>();
	}
	
	public Texture getTexture(String path)
	{
		for(Texture texture : m_Textures)
		{
			if(texture.getPath().equals(path))
			{
				return texture;
			}
		}
		
		Texture texture = new Texture(path);
		m_Textures.add(texture);
		return texture;
	}
	
	public Font getFont(String name)
	{
		for(Font font : m_Fonts)
		{
			if(font.getName().equals(name))
			{
				return font;
			}
		}
		
		Font font = new Font(name);
		m_Fonts.add(font);
		return font;	    
	}
	
	public Renderable3D[] getModel(String model, String shader, String texture)
	{
		//load a mesh
		for(Renderable3D[] meshes : m_Meshes)
		{
			if(meshes[0].getFilename().equals(model))
			{
				return meshes;
			}
		}
		
		Texture tex = this.getTexture(texture);
		ShaderProgram shaderProgram = new ShaderProgram();
		shaderProgram.loadShader(shader);
		
		//Parse the file
		AIScene aiScene = Assimp.aiImportFile(model, 0);
		if (aiScene == null) {
		    System.out.println("Error loading model: " + model);
		}
		
		int numMaterials = aiScene.mNumMaterials();
		PointerBuffer aiMaterials = aiScene.mMaterials();
		List<Material> materials = new ArrayList<>();
		for (int i = 0; i < numMaterials; i++) {
		    AIMaterial aiMaterial = AIMaterial.create(aiMaterials.get(i));   
		}
		
		return null;
	}
	
	private ArrayList<Material> processMaterials()
	{
		//for later
		return null;
	}
	
	public void cleanup()
	{
		for(Font font : m_Fonts)
		{
			font.destroy();
		}
	}
}