package engine.scene;

import java.util.ArrayList;

import engine.entity.Entity;

public class Scene
{
	private int m_ID;
	private ArrayList<Entity> m_Entities;
	
	public Scene(int id)
	{
		m_ID = id;
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
		
	}
	
	public void render()
	{
		
	}
}
