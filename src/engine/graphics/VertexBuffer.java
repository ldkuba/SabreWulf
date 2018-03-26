package engine.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

/**
 * Stores information about vertices and their layout
 * 
 * @author SabreWulf
 *
 */
public class VertexBuffer {
	private int m_ID;
	private int m_Count;
	private VertexLayout m_Layout;
	private VertexBufferUsage m_Usage;

	/**
	 * Used to mark the vertex buffer for STATIC_DRAW or DYNAMIC_DRAW
	 * 
	 * @author SabreWulf
	 *
	 */
	public enum VertexBufferUsage {
		STATIC, DYNAMIC
	};

	/**
	 * Creates an empty vertex buffer
	 */
	public VertexBuffer() {
		m_ID = GL15.glGenBuffers();
	}

	/**
	 * Creates a vertex buffer and fills it with data
	 * @param layout - the layout of this vertex buffer
	 * @param data - the vertex data
	 * @param count - the number of vertices
	 * @param usage - buffer usage
	 */
	public VertexBuffer(VertexLayout layout, ByteBuffer data, int count, VertexBufferUsage usage)
	{
		m_ID = GL15.glGenBuffers();
		updateData(layout, data, count, usage, true);
	}

	
	/**
	 * Creates a vertex buffer and fills it with data
	 * 
	 * @param layout
	 *            - the layout of this vertex buffer
	 * @param data
	 *            - the vertex data
	 * @param count
	 *            - the number of vertices
	 * @param usage
	 *            - buffer usage
	 */
	public VertexBuffer(VertexLayout layout, float[] data, int count, VertexBufferUsage usage) {
		m_ID = GL15.glGenBuffers();

		updateData(layout, data, count, usage, true);
	}

	/**
	 * Updates the contents of this vertex buffer
	 * 
	 * @param layout
	 *            - the layout of this vertex buffer
	 * @param data
	 *            - the vertex data
	 * @param count
	 *            - the number of vertices
	 * @param usage
	 *            - buffer usage
	 * @param alloc
	 *            - used to mark whether new memory should be allocated for the
	 *            buffer or the old one should be used
	 */
	public void updateData(VertexLayout layout, ByteBuffer data, int count, VertexBufferUsage usage, boolean alloc) {
		m_Count = count;
		m_Usage = usage;
		m_Layout = layout;

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_ID);

		if (m_Usage.equals(VertexBufferUsage.STATIC)) {
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		} else {
			if (alloc) {
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
			} else {
				GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, data);
			}
		}

		long offset = 0;
		for (int i = 0; i < layout.getAttributeCount(); i++) {
			GL20.glEnableVertexAttribArray(i);
			GL20.glVertexAttribPointer(i, layout.getVertexAttribute(i).getCount(),
					layout.getVertexAttribute(i).getType().getID(), layout.getVertexAttribute(i).isNormalized(),
					layout.getVertexSizeInBytes(), offset);

			offset += layout.getVertexAttribute(i).getSizeInBytes();
		}
	}

	/**
	 * Updates the contents of this vertex buffer
	 * 
	 * @param layout
	 *            - the layout of this vertex buffer
	 * @param data
	 *            - the vertex data
	 * @param count
	 *            - the number of vertices
	 * @param usage
	 *            - buffer usage
	 * @param alloc
	 *            - used to mark whether new memory should be allocated for the
	 *            buffer or the old one should be used
	 */
	public void updateData(VertexLayout layout, float[] data, int count, VertexBufferUsage usage, boolean alloc) {
		m_Count = count;
		m_Usage = usage;
		m_Layout = layout;

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_ID);

		if (m_Usage.equals(VertexBufferUsage.STATIC)) {
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		} else {
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
		}

		long offset = 0;
		for (int i = 0; i < layout.getAttributeCount(); i++) {
			GL20.glEnableVertexAttribArray(i);
			GL20.glVertexAttribPointer(i, layout.getVertexAttribute(i).getCount(),
					layout.getVertexAttribute(i).getType().getID(), layout.getVertexAttribute(i).isNormalized(),
					layout.getVertexSizeInBytes(), offset);

			offset += layout.getVertexAttribute(i).getSizeInBytes();
		}
	}

	/**
	 * Binds this vertex buffer
	 */
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, m_ID);
	}

	/**
	 * Unbinds this vertex buffer
	 */
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Deletes this vertex buffer
	 */
	public void close() {
		GL15.glDeleteBuffers(m_ID);
	}
}