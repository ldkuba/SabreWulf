package engine.font;

import engine.graphics.texture.Texture;
import engine.graphics.texture.TextureAtlas;
import engine.maths.Vec2;

public class Font {
	private TextureAtlas m_Texture;
	private String m_Name;

	public Font(String name) {
		m_Name = name;
		Texture texture = new Texture("res/fonts/" + name);
		m_Texture = new TextureAtlas(texture, 16, 16); // 16 characters in a row
	}

	public Vec2[] getUVs(char c) {
		Vec2[] resultUV = m_Texture.getElementUVs(c - 31);		
		return resultUV;
	}

	public Texture getTexture() {
		return m_Texture.getTexture();
	}

	public String getName() {
		return m_Name;
	}

	public void destroy() {

	}
}
