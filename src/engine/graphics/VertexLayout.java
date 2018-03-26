package engine.graphics;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

/**
 * Representation of the layout of vertex data
 * 
 * @author SabreWulf
 *
 */
public class VertexLayout {
	public enum AttributeTypes {
		Int(GL11.GL_INT), Float(GL11.GL_FLOAT), Vec3(GL11.GL_FLOAT), Vec4(GL11.GL_FLOAT), Mat4(GL11.GL_FLOAT);

		private final int m_ID;

		AttributeTypes(int id) {
			m_ID = id;
		}

		public int getID() {
			return m_ID;
		}
	}

	private int m_Count;

	private ArrayList<VertexAttribute> m_Layout;

	/**
	 * Creates an empty vertex layout
	 */
	public VertexLayout() {
		m_Layout = new ArrayList<>();
		m_Count = 0;
	}

	/**
	 * Adds an attribute to the vertex layout
	 * 
	 * @param attrib
	 *            - the attribute to add
	 * @param count
	 *            - number of attributes of this type to add
	 */
	public void addAttribute(VertexAttribute attrib, int count) {
		m_Count += 1;

		for (int i = 0; i < count; i++)
			m_Layout.add(attrib);
	}

	/**
	 * 
	 * @return number of attributes per vertex
	 */
	public int getAttributeCount() {
		return m_Count;
	}

	/**
	 * Gets vertex attribute at index
	 * 
	 * @param index
	 *            - index of attribute
	 * @return vertex attribute at index
	 */
	public VertexAttribute getVertexAttribute(int index) {
		return m_Layout.get(index);
	}

	/**
	 * Gets the size of one vertex in bytes
	 * 
	 * @return size of vertex in bytes
	 */
	public int getVertexSizeInBytes() {
		int size = 0;

		for (int i = 0; i < m_Count; i++) {
			for (int j = 0; j < m_Layout.get(i).getCount(); j++) {
				if (m_Layout.get(i).getType().compareTo(AttributeTypes.Int) == 0) {
					size += VertexAttribute.getIntSize();
				} else if (m_Layout.get(i).getType().compareTo(AttributeTypes.Float) == 0) {
					size += VertexAttribute.getFloatSize();
				} else if (m_Layout.get(i).getType().compareTo(AttributeTypes.Vec3) == 0) {
					size += VertexAttribute.getVec3Size();
				} else if (m_Layout.get(i).getType().compareTo(AttributeTypes.Vec4) == 0) {
					size += VertexAttribute.getVec4Size();
				} else if (m_Layout.get(i).getType().compareTo(AttributeTypes.Mat4) == 0) {
					size += VertexAttribute.getMat4Size();
				}
			}
		}
		return size;
	}

}
