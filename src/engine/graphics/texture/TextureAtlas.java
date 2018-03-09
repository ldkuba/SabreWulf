package engine.graphics.texture;

import engine.maths.Vec2;

public class TextureAtlas {
	private Texture m_Texture;
	private int m_ElementCountX;
	private int m_ElementCountY;
	private int m_ElementWidth, m_ElementHeight;

	public TextureAtlas(Texture texture, int elementCountX, int elementCountY) {
		m_ElementCountX = elementCountX;
		m_ElementCountY = elementCountY;

		m_Texture = texture;
		m_ElementWidth = m_Texture.getWidth() / m_ElementCountX;
		m_ElementHeight = m_Texture.getHeight() / m_ElementCountY;

	}

	public Vec2[] getElementUVs(int i) {

		int ix = i % m_ElementCountX;
		int iy = i / m_ElementCountY;

		Vec2[] resultUVs = new Vec2[4];

		int topLeftX = m_ElementWidth * ix;
		int topLeftY = m_ElementHeight * iy;
		Vec2 topLeft = new Vec2(topLeftX, topLeftY);
		Vec2 topRight = new Vec2(topLeftX + m_ElementWidth, topLeftY);
		Vec2 btmLeft = new Vec2(topLeftX, topLeftY + m_ElementHeight);
		Vec2 btmRight = new Vec2(topLeftX + m_ElementWidth, topLeftY + m_ElementHeight);
		
		topLeft.scale(1/((float)(m_Texture.getWidth())));
		topRight.scale(1/((float)(m_Texture.getWidth())));
		btmLeft.scale(1/((float)(m_Texture.getWidth())));
		btmRight.scale(1/((float)(m_Texture.getWidth())));

		resultUVs[0] = topLeft;
		resultUVs[1] = btmLeft;
		resultUVs[2] = btmRight;
		resultUVs[3] = topRight;

		return resultUVs;
	}

	public Texture getTexture() {
		return m_Texture;
	}

	public int getElementCountX() {
		return m_ElementCountX;
	}

	public int getElementCountY() {
		return m_ElementCountY;
	}

	public int getElementWidth() {
		return m_ElementWidth;
	}

	public int getElementHeight() {
		return m_ElementHeight;
	}
}
