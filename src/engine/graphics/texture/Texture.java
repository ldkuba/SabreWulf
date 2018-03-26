package engine.graphics.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

/**
 * Representation of a texture
 * @author nawro
 *
 */
public class Texture
{
	private int m_ID;
	private ByteBuffer m_ImageData;
	private int m_Width, m_Height, m_ColourChannels;

	private String m_Path;

	/**
	 * Creates a texture from the specified file
	 * @param filename
	 */
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
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, m_Width, m_Height, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, m_ImageData);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		unbind(0);

		STBImage.stbi_image_free(m_ImageData);

		m_Path = filename;
	}

	/**
	 * Creates a texture from the provided data
	 * @param data - texture data
	 * @param width - width of the texture
	 * @param height - height of the texture
	 * @param name - reference name for the asset loader
	 */
	public Texture(ByteBuffer data, int width, int height, String name)
	{

		m_ImageData = data;

		m_ImageData.flip();

		m_Width = width;
		m_Height = height;
		m_ColourChannels = 4;

		m_ID = GL11.glGenTextures();

		bind(0);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, m_Width, m_Height, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, m_ImageData);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		unbind(0);

		m_Path = name;
	}

	/**
	 * Binds this material to  agiven slot
	 * @param slot - slot to bind this material to
	 */
	public void bind(int slot)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, m_ID);
	}

	/**
	 * Unbinds texture at the specified slot
	 * @param slot - the slot which should be unbound
	 */
	public void unbind(int slot)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + slot);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Gets the width of this texture
	 * @return the width of this texture
	 */
	public int getWidth()
	{
		return m_Width;
	}

	/**
	 * Gets the height of this texture
	 * @return the height of this texture
	 */
	public int getHeight()
	{
		return m_Height;
	}

	/**
	 * Returns the OpenGL id for this texture
	 * @return the id for this texture
	 */
	public int getID()
	{
		return m_ID;
	}

	/**
	 * Returns the resource path for this texture
	 * @return
	 */
	public String getPath()
	{
		return m_Path;
	}
}
