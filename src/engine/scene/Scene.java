package engine.scene;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.camera.Camera;
import engine.graphics.renderer.Renderer;
import engine.graphics.renderer.Renderer2D;

public class Scene
{
	private int m_ID;
	private ArrayList<Entity> m_Entities;
	
	private Renderer m_Renderer;
	private Camera camera;
	
	public Scene(int id)
	{
		m_ID = id;
		
		m_Renderer = new Renderer2D();
		
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
				//sprite.render();
			}
		}
	}
}
