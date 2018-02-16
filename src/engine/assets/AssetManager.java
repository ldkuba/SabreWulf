package engine.assets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

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
	
	public Texture getFont(String name)
	{
		Path p = FileSystems.getDefault().getPath("res/fonts", name);
		
		byte[] fontBytes = new byte[0];
		
		try
		{
			fontBytes = Files.readAllBytes(p);
		}catch (IOException e)
		{
			e.printStackTrace();
		}

		ByteBuffer fontBuffer = BufferUtils.createByteBuffer(fontBytes.length);
		fontBuffer.put(fontBytes, 0, fontBytes.length);
		
		//stbtt_BakeFontBitmap(fontBuffer, 0, 32.0, temp_bitmap,512,512, 32,96, cdata); // no guarantee this fits!
		
		return null;
		    
	}
}