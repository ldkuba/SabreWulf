package engine.graphics.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class Texture
{
	private int m_ID;
	private ByteBuffer m_ImageData;
	private int m_Width, m_Height, m_ColourChannels;
	
	public Texture(String filename)
	{
		IntBuffer x = BufferUtils.createIntBuffer(1);
		IntBuffer y = BufferUtils.createIntBuffer(1);
		IntBuffer colourChannels = BufferUtils.createIntBuffer(1);
		
		m_ImageData = STBImage.stbi_load(filename, x, y, colourChannels, 0);
		
		m_Width = x.get(0);
		m_Height = y.get(0);
		m_ColourChannels = colourChannels.get(0);	
		
		m_ID = GL11.glGenTextures();
		
		bind();
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, m_Width, m_Height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, m_ImageData);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		unbind();
		
		STBImage.stbi_image_free(m_ImageData);
	}
	
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, m_ID);
	}
	
	public void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int getWidth()
	{
		return m_Width;
	}
	
	public int getHeight()
	{
		return m_Height;
	}
}
