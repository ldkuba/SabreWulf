package engine.assets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;

import engine.font.Font;
import engine.graphics.texture.Texture;

public class AssetManager
{
	private ArrayList<Texture> m_Textures;
	private ArrayList<Font> m_Fonts;
	
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
	
	public void cleanup()
	{
		for(Font font : m_Fonts)
		{
			font.destroy();
		}
	}
}