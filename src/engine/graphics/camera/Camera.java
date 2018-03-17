package engine.graphics.camera;

import engine.application.Application;
import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;

public class Camera {
	private Mat4 m_ViewMatrix;
	private Mat4 m_ProjectionMatrix;

	private Vec3 m_Position;
	private Vec3 m_Direction;

	public Camera() {
		
		m_ViewMatrix = Mat4.identity();
		m_ProjectionMatrix = Mat4.identity();
		
		m_Position = new Vec3();
		m_Direction = new Vec3(0, 0, 1.0f);

		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}

	public Vec3 getPosition() {
		return m_Position;
	}

	public void setPosition(Vec3 position) {
		m_Position = position;

		// update ViewMatrix
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}

	public Vec3 getDirection() {
		return m_Direction;
	}

	public void setDirection(Vec3 direction) {
		m_Direction = direction;

		// update ViewMatrix
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}

	public Mat4 getViewMatrix() {
		return m_ViewMatrix;
	}

	public Mat4 getProjectionMatrix() {
		return m_ProjectionMatrix;
	}

	public void setProjectionMatrix(Mat4 projectionMatrix) {
		m_ProjectionMatrix = projectionMatrix;
	}
	
	// Helper methods

	public void move(Vec3 movement) {
		this.setPosition(Vec3.add(m_Position, movement));
	}

	public Vec3 getWorldCoordinates(float screenX, float screenY) {

		Vec2 windowSize = Application.s_WindowSize;
		float scaleX = Application.s_Viewport.getX() / windowSize.getX();
		float scaleY = Application.s_Viewport.getY() / windowSize.getY();
		float newScreenX = screenX * scaleX;
		float newScreenY = screenY * scaleY;

		Vec3 result = new Vec3();
		result.setX((newScreenX - Application.s_Viewport.getX()/2.0f));
		result.setY(-1.0f * (newScreenY - Application.s_Viewport.getY()/2.0f));
		
		result.scale(2.0f);
		
		result = Vec3.add(result, m_Position);
		
		result.setZ(0.0f);

		return result;
	}
	
	public Vec3 getScreenCoordinates(float worldX, float worldY){
		Vec2 windowSize = Application.s_WindowSize;
		float scaleX = windowSize.getX() / Application.s_Viewport.getX();
		float scaleY = windowSize.getY() / Application.s_Viewport.getY();
		float newX = worldX * scaleX;
		float newY = worldY * scaleY;
		Vec3 pos = new Vec3();
		pos.setX((Application.s_Viewport.getX()/2.0f - newX));
		pos.setY(1.0f * (Application.s_Viewport.getY()/2.0f) - newY);
		pos.scale(-2.0f);
		pos = Vec3.add(pos, m_Position);
		pos.setZ(0.0f);
		return pos;
	}
}
