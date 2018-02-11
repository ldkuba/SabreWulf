package engine.graphics.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class Texture
{
	private int m_ID;
	private ByteBuffer m_ImageData;
	private int m_Width, m_Height, m_ColourChannels;
	
	private String m_Path;
	
	public Texture(String filename)
	{
		IntBuffer x = BufferUtils.createIntBuffer(1);
		IntBuffer y = BufferUtils.createIntBuffer(1);
		IntBuffer colourChannels = BufferUtils.createIntBuffer(1);
		
		m_ImageData = STBImage.stbi_load(filename, x, y, colourChannels, STBImage.STBI_rgb_alpha);
		
		m_ImageData.flip();
		
		m_Width = x.get(0);
		m_Height = y.get(0);
		m_ColourChannels = colourChannels.get(0);	
		
		m_ID = GL11.glGenTextures();
		
		bind(0);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, m_Width, m_Height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, m_ImageData);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		unbind(0);
		
		STBImage.stbi_image_free(m_ImageData);
		
		m_Path = filename;
	}
	
	public void bind(int slot)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, m_ID);
	}
	
	public void unbind(int slot)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int getWidth(int slot)
	{
		return m_Width;
	}
	
	public int getHeight()
	{
		return m_Height;
	}
	
	public int getID()
	{
		return m_ID;
	}
	
	public String getPath()
	{
		return m_Path;
	}
}
