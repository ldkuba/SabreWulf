package engine.entity;

import java.util.ArrayList;

import engine.entity.component.AbstractComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;

/**
 * 
 * @author SabreWulf
 *
 */
public class Entity {

	private int id;
	private boolean shouldBeCulled;
	private String name;
	private ArrayList<AbstractComponent> components;
	private ArrayList<String> tags;
	
	public Entity(String name) {
		this.shouldBeCulled = false;
		this.id = -1;
		this.name = name;
		components = new ArrayList<AbstractComponent>();
		
		tags = new ArrayList<>();
	}
	
	public Entity(String name, boolean culled) {
		this.shouldBeCulled = culled;
		this.id = -1;
		this.name = name;
		components = new ArrayList<AbstractComponent>();
		
		tags = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addTag(String tag)
	{
		if(!tags.contains(tag))
		{
			tags.add(tag);
			
		}
	}

	public boolean hasTag(String tag)
	{
		return tags.contains(tag);
	}
	
	public void addComponent(AbstractComponent comp) {
		if (!hasComponent(comp.getClass())) {
			components.add(comp);
		}
	}

	public void removeComponent(AbstractComponent comp) {
		if (hasComponent(comp.getClass())) {
			components.remove(comp);
		}
	}

	public ArrayList<AbstractComponent> getComponents() {
		return components;
	}

	public AbstractComponent getComponent(Class componentType) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getClass().equals(componentType)) {
				return components.get(i);
			}
		}
		
		return null;
	}

	public boolean hasComponent(Class componentType) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getClass().equals(componentType)) {
				return true;
			}
		}
		return false;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	//for simplicity because its used often	
	public TransformComponent getTransform()
	{
		return (TransformComponent) getComponent(TransformComponent.class);
	}
	
	public SpriteComponent getSprite()
	{
		return (SpriteComponent) getComponent(SpriteComponent.class);
	}
	
	public NetTransformComponent getNetTransform()
	{
		return (NetTransformComponent) getComponent(NetTransformComponent.class);
	}
	
	public Entity clone()
	{
		Entity result = new Entity(name);
		for(AbstractComponent comp : components)
		{
			result.addComponent(comp);
		}
		
		return result;
	}
	
	public boolean shouldBeCulled(){
		return shouldBeCulled;
	}
}
