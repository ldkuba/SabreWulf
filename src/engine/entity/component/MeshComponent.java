package engine.entity.component;

import engine.graphics.renderer.Renderable3D;
import engine.graphics.renderer.Renderer3D;
import engine.maths.Mat4;

public class MeshComponent extends AbstractComponent
{
	private Renderable3D[] m_Meshes;
	
	public MeshComponent(Renderable3D[] meshes)
	{
		m_Meshes = meshes;
	}
	
	public Renderable3D[] getMeshes()
	{
		return m_Meshes;
	}
	
	public void draw(Renderer3D renderer, Mat4 modelMatrix)
	{
		for(int i = 0; i < m_Meshes.length; i++)
		{
			m_Meshes[i].draw(renderer, modelMatrix);
		}
	}

	@Override
	public void update()
	{
	}
}
