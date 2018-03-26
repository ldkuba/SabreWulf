package engine.graphics.renderer;

import engine.graphics.VertexAttribute;
import engine.graphics.VertexLayout;
import engine.graphics.VertexLayout.AttributeTypes;
import engine.graphics.texture.Texture;
import engine.maths.Vec2;
import engine.maths.Vec4;

public class Renderable2D {
	private static final int VERTEX_COUNT = 4;
	private static final int INDEX_COUNT = 6;
	private static final int VERTEX_PRIMITIVE_ATTRIB_COUNT = 3 + 2 + 4;

	private static VertexLayout s_VertexLayout;
	private float m_Width, m_Height;

	private Vec2[] m_UVs;

	private Texture m_Texture;
	private Vec4 m_Color;

	private Vec4 m_OverlayColor;

	public Renderable2D(float width, float height, Vec4 color, Texture texture) {
		this(width, height, color);

		m_Texture = texture;

		m_OverlayColor = new Vec4(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public Renderable2D(float width, float height, Vec4 color) {
		m_Color = color;
		m_Width = width;
		m_Height = height;

		m_UVs = new Vec2[4];
		m_UVs[0] = new Vec2(0.0f, 0.0f);
		m_UVs[1] = new Vec2(0.0f, 1.0f);
		m_UVs[2] = new Vec2(1.0f, 1.0f);
		m_UVs[3] = new Vec2(1.0f, 0.0f);

		m_OverlayColor = new Vec4(0.0f, 0.0f, 0.0f, 0.0f);
	}

	public static VertexLayout getVertexLayout() {
		if (s_VertexLayout == null) {
			s_VertexLayout = new VertexLayout();
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1);
			// position (x, y, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1);
			// UV (x, z)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 1, false), 1);
			// textureSlot
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1);
			// colour (r, g, b, a)
			s_VertexLayout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1);
			// overlay colour (r, g, b, a)
		}

		return s_VertexLayout;
	}

	public float getWidth() {
		return m_Width;
	}

	public float getHeight() {
		return m_Height;
	}

	public void setWidth(float width) {
		m_Width = width;
	}

	public void setHeight(float height) {
		m_Height = height;
	}

	public Vec4 getColor() {
		return m_Color;
	}

	public void setColor(Vec4 newColor) {
		m_Color = newColor;
	}

	public Vec2[] getUVs() {
		return m_UVs;
	}

	public void setUVs(Vec2[] UVs) {
		m_UVs = UVs;
	}

	public Texture getTexture() {
		return m_Texture;
	}

	public void setTexture(Texture texture) {
		m_Texture = texture;
	}

	public void setOverlayColor(Vec4 overlayColor) {
		m_OverlayColor = overlayColor;
	}

	public Vec4 getOverlayColor() {
		return m_OverlayColor;
	}
}
