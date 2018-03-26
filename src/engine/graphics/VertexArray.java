package engine.graphics;

import org.lwjgl.opengl.GL30;

public class VertexArray {
	private int m_ID;

	private VertexBuffer m_VertexBuffer;

	public VertexArray() {
		m_ID = GL30.glGenVertexArrays();
	}

	/**
	 * Add vertex buffer
	 * THE VAO MUST BE BOUND WHEN CALLING THIS FUNCTION
	 * @param buffer
	 */
	public void addVertexBuffer(VertexBuffer buffer) {
		m_VertexBuffer = buffer;
	}

	/**
	 * Get vertx buffer
	 * @return
	 */
	public VertexBuffer getVertexBuffer() {
		return m_VertexBuffer;
	}

	/**
	 * Bind the array
	 */
	public void bind() {
		GL30.glBindVertexArray(m_ID);
	}

	/**
	 * Unbind the array
	 */
	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	/**
	 * Delete
	 */
	public void close() {
		m_VertexBuffer.close();
		GL30.glDeleteVertexArrays(m_ID);
	}
}
