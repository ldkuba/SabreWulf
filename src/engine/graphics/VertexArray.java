package engine.graphics;

import org.lwjgl.opengl.GL30;

/**
 * Object used to store information about vertex data and their layout
 * 
 * @author SabreWulf
 *
 */
public class VertexArray {
	private int m_ID;

	private VertexBuffer m_VertexBuffer;

	/**
	 * Generates empty vertex array
	 */
	public VertexArray() {
		m_ID = GL30.glGenVertexArrays();
	}

	/**
	 * Sets the buffer used by this vertex array. THE VAO MUST BE BOUND WHEN
	 * CALLING THIS FUNCTION
	 * 
	 * @param buffer
	 *            - vertex buffer used by this vertex array
	 */
	public void addVertexBuffer(VertexBuffer buffer) {
		m_VertexBuffer = buffer;
	}

	/**
	 * 
	 * @return the vertex buffer used by this vertex array
	 */
	public VertexBuffer getVertexBuffer() {
		return m_VertexBuffer;
	}

	/**
	 * Binds the vertex array
	 */
	public void bind() {
		GL30.glBindVertexArray(m_ID);
	}

	/**
	 * Unbinds this vertex array
	 */
	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	/**
	 * Deletes this vertex array and the vertex buffer assigned to it
	 */
	public void close() {
		m_VertexBuffer.close();
		GL30.glDeleteVertexArrays(m_ID);
	}
}
