package engine.graphics.camera;

import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec3;

public class Camera
{
	private Mat4 m_ViewMatrix;
	private Mat4 m_ProjectionMatrix;
	
	private Vec3 m_Position;
	private Vec3 m_Direction;
	
	public Camera()
	{
		m_ViewMatrix = Mat4.identity();
		m_ProjectionMatrix = Mat4.identity();
		
		m_Position = new Vec3();
		m_Direction = new Vec3(0, 0, 1.0f);
		
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}
		
	public Vec3 getPosition()
	{
		return m_Position;
	}
	
	public void setPosition(Vec3 position)
	{
		m_Position = position;
		
		//update ViewMatrix
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}
	
	public Vec3 getDirection()
	{
		return m_Direction;
	}
	
	public void setDirection(Vec3 direction)
	{
		m_Direction = direction;
		
		//update ViewMatrix
		m_ViewMatrix = MathUtil.simpleViewMat(m_Position, m_Direction, new Vec3(0, 1.0f, 0));
	}
	
	public Mat4 getViewMatrix()
	{		
		return m_ViewMatrix;
	}
	
	public Mat4 getProjectionMatrix()
	{
		return m_ProjectionMatrix;
	}
	
	public void setProjectionMatrix(Mat4 projectionMatrix)
	{
		m_ProjectionMatrix = projectionMatrix;
	}
}