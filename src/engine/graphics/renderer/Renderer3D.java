package engine.graphics.renderer;

import engine.graphics.camera.Camera;
import engine.maths.Vec3;

public class Renderer3D {
	// Lighting for future goes here, UPDATE: The future is now
	private Vec3 m_AmbientLight;

	private Camera m_CurrentCamera;

	public Renderer3D(Camera camera) {
		m_CurrentCamera = camera;
		m_AmbientLight = new Vec3(0.8f, 0.8f, 0.8f);
	}

	public void setCamera(Camera camera) {
		m_CurrentCamera = camera;
	}

	public Camera getCamera() {
		return m_CurrentCamera;
	}

	public void setAmbientLight(Vec3 ambientLight) {
		m_AmbientLight = ambientLight;
	}

	public Vec3 getAmbientLight() {
		return m_AmbientLight;
	}
}
