package engine.graphics.renderer;

import engine.graphics.camera.Camera;
import engine.maths.Mat4;

public class Renderer3D extends Renderer
{	
	//Lighting for future goes here
	
	private Camera m_CurrentCamera;	
	
	public Renderer3D(Camera camera)
	{
		m_CurrentCamera = camera;
	}
	
	public void setCamera(Camera camera)
	{
		m_CurrentCamera = camera;
	}
	
	public Camera getCamera()
	{
		return m_CurrentCamera;
	}
}
