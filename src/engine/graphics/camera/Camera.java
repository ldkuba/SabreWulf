package engine.graphics.camera;

import engine.application.Application;
import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;

/**
 * The camera is used so that the user can view the game and navigate around
 * 
 * @author SabreWulf
 *
 */
public class Camera {

	private Mat4 m_ViewMatrix;
	private Mat4 m_ProjectionMatrix;
	private Vec3 m_Position;
	private Vec3 m_Direction;

	/**
	 * Create a new camera
	 */
	public Camera() {
		m_ViewMatrix = Mat4.identity();
		m_ProjectionMatrix = Mat4.identity();
		m_Position = new Vec3();
		m_Direction = new Vec3(0, 0, 1.0f);
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}

	/**
	 * Get the current origin position of the camera
	 * 
	 * @return
	 */
	public Vec3 getPosition() {
		return m_Position;
	}

	/**
	 * Set the current view position of the camera
	 * 
	 * @param position
	 */
	public void setPosition(Vec3 position) {
		m_Position = position;
		/* update ViewMatrix */
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}

	/**
	 * Get the direction that the camera is facing
	 * 
	 * @return
	 */
	public Vec3 getDirection() {
		return m_Direction;
	}

	/**
	 * Set the direction of the camera
	 * 
	 * @param direction
	 */
	public void setDirection(Vec3 direction) {
		m_Direction = direction;
		/* update ViewMatrix */
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}

	/**
	 * Get the view matrix of the camera
	 * 
	 * @return
	 */
	public Mat4 getViewMatrix() {
		return m_ViewMatrix;
	}

	/**
	 * Get the projection matrix for the camera
	 * 
	 * @return
	 */
	public Mat4 getProjectionMatrix() {
		return m_ProjectionMatrix;
	}

	/**
	 * Set the cameras projection matrix
	 * 
	 * @param projectionMatrix
	 */
	public void setProjectionMatrix(Mat4 projectionMatrix) {
		m_ProjectionMatrix = projectionMatrix;
	}

	// Helper methods
	/**
	 * Move the camera to a new position
	 * 
	 * @param movement
	 */
	public void move(Vec3 movement) {
		this.setPosition(Vec3.add(m_Position, movement));
	}

	/**
	 * Convert the game screen position of an entity to the devices window
	 * position (as a percentage position).
	 * 
	 * @param screenX
	 * @param screenY
	 * @return
	 */
	public Vec3 getWorldCoordinates(float screenX, float screenY) {
		Vec2 windowSize = Application.s_WindowSize;
		float scaleX = Application.s_Viewport.getX() / windowSize.getX();
		float scaleY = Application.s_Viewport.getY() / windowSize.getY();
		float newScreenX = screenX * scaleX;
		float newScreenY = screenY * scaleY;

		Vec3 result = new Vec3();
		result.setX((newScreenX - Application.s_Viewport.getX() / 2.0f));
		result.setY(-1.0f * (newScreenY - Application.s_Viewport.getY() / 2.0f));
		result.scale(2.0f);
		result = Vec3.add(result, m_Position);
		result.setZ(0.0f);

		return result;
	}

	/**
	 * Convert the window position of an entity to a game screen position
	 * @param worldPos
	 * @return
	 */
	public Vec2 getScreenCoordinates(Vec3 worldPos) {
		float newX = (50.0f / (Application.s_Viewport.getX()))
				* (worldPos.getX() - m_Position.getX() + Application.s_Viewport.getX());
		float newY = (50.0f / (Application.s_Viewport.getY()))
				* (-worldPos.getY() + m_Position.getY() + +Application.s_Viewport.getY());
		Vec2 pos = new Vec2();
		pos.setX(newX);
		pos.setY(newY);

		return pos;
	}
}
