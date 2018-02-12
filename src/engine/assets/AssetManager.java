package engine.assets;

import java.util.ArrayList;

import engine.graphics.texture.Texture;

public class AssetManager
{
	private ArrayList<Texture> m_Textures;
	
	public AssetManager()
	{	
		m_Textures = new ArrayList<>();
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
}