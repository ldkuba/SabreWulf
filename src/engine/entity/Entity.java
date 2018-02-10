package engine.entity;

import java.util.ArrayList;

import engine.entity.component.AbstractComponent;
import engine.entity.component.TransformComponent;

public class Entity
{

	private int id;
	private String name;
	private ArrayList<AbstractComponent> components;

	public Entity(int id, String name)
	{
		this.id = id;
		this.name = name;
		components = new ArrayList<AbstractComponent>();
	}

	public void addComponent(AbstractComponent comp)
	{
		if(!hasComponent(comp.getClass()))
		{
			components.add(comp);
		}
	}

	public void removeComponent(AbstractComponent comp)
	{
		if(hasComponent(comp.getClass()))
		{
			components.remove(comp);
		}
	}

	public ArrayList<AbstractComponent> getComponents()
	{
		return components;
	}

	public AbstractComponent getComponent(Class componentType)
	{
		for (int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getClass().equals(componentType))
			{
				return components.get(i);
			}
		}
		
		return null;
	}
	
	public boolean hasComponent(Class componentType)
	{
		for (int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getClass().equals(componentType))
			{
				return true;
			}
		}
		return false;
	}
	
	//for simplicity because its used often	
	public TransformComponent getTransform()
	{
		return (TransformComponent) getComponent(TransformComponent.class);
	}
}
