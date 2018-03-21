package engine.font;

import engine.graphics.texture.Texture;
import engine.graphics.texture.TextureAtlas;
import engine.maths.Vec2;

/**
 * Set up fonts to display text on the GUI
 * @author SabreWulf
 *
 */
public class Font {
	private TextureAtlas m_Texture;
	private String m_Name;

	/**
	 * Initialise the font with the give name
	 * @param name
	 */
	public Font(String name) {
		m_Name = name;
		Texture texture = new Texture("res/fonts/" + name);
		m_Texture = new TextureAtlas(texture, 16, 16); // 16 characters in a row
	}

	/**
	 * Fet the font UV's
	 * @param c
	 * @return
	 */
	public Vec2[] getUVs(char c) {
		Vec2[] resultUV = m_Texture.getElementUVs(c - 32);		
		return resultUV;
	}

	/**
	 * Get the texture for the given font
	 * @return
	 */
	public Texture getTexture() {
		return m_Texture.getTexture();
	}

	/**
	 * Get the name of the font
	 * @return
	 */
	public String getName() {
		return m_Name;
	}

	/**
	 * Destroy the font
	 */
	public void destroy() {

	}
}
