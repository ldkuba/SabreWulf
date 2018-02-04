package engine.scene;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.camera.Camera;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.renderer.Renderer3D;
import engine.maths.Mat4;

public class Scene
{
	private int m_ID;
	private ArrayList<Entity> m_Entities;
	
	private Renderer m_Renderer2D;
	private Renderer m_Renderer3D; //Currently not used
	private Camera m_Camera;
	
	public Scene(int id)
	{
		m_ID = id;
		
		m_Renderer2D = new Renderer2D();
		m_Renderer3D = new Renderer3D();
		
	}
	
	public void addEntity(Entity e)
	{
		if(!m_Entities.contains(e))
		{
			m_Entities.add(e);
		}
	}
	
	public void removeEntity(Entity e)
	{
		m_Entities.remove(e);
	}
	
	public void update()
	{
		for(Entity e : m_Entities)
		{
			if(e.hasComponent(TransformComponent.class))
			{
				TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
				//transform.update(); just an example, the transform component doesnt need automatic updating
			}
		}
	}
	
	public void render()
	{
		for(Entity e : m_Entities)
		{
			if(e.hasComponent(SpriteComponent.class))
			{
				SpriteComponent sprite = (SpriteComponent) e.getComponent(SpriteComponent.class);
				
				Mat4 transformation = Mat4.identity();
				
				if(e.hasComponent(TransformComponent.class))
				{
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}
				
				//TODO sprite.submit(m_Renderer2D, transformation);
			}
		}
		
		//TODO m_Renderer2D.drawAll();
	}
}
