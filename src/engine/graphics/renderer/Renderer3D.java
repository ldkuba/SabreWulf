package engine.graphics.renderer;

import engine.graphics.camera.Camera;
import engine.maths.Vec3;

/**
 * 3D Renderer with basic lighting capabilities
 * @author nawro
 *
 */
public class Renderer3D {
	private Vec3 m_AmbientLight;
	private Camera m_CurrentCamera;
	
	/**
	 * Creates the renderer and sets the current camera
	 * @param camera - current cumera to use
	 */
	public Renderer3D(Camera camera)
	{
		m_CurrentCamera = camera;
		m_AmbientLight = new Vec3(0.8f, 0.8f, 0.8f);
	}
	
	/**
	 * Sets the current camera to use
	 * @param camera - current camera to use
	 */
	public void setCamera(Camera camera)
	{
		m_CurrentCamera = camera;
	}
	
	/**
	 * Gets the currently used camera
	 * @return currently used camera
	 */
	public Camera getCamera()
	{
		return m_CurrentCamera;
	}
	
	/**
	 * Sets the ambient light value for light calculations
	 * @param ambientLight - the ambient light value
	 */
	public void setAmbientLight(Vec3 ambientLight)
	{
		m_AmbientLight = ambientLight;
	}
	
	/**
	 * Gets the ambient light value
	 * @return ambient light value
	 */
	public Vec3 getAmbientLight()
	{
		return m_AmbientLight;
	}
}
