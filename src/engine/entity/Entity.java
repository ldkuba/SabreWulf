package engine.entity;

import java.util.ArrayList;

import engine.entity.component.AbstractComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;

/**
 * Generic class use to represent objects in the game
 * 
 * @author SabreWulf
 *
 */
public class Entity {

	private int id;
	private boolean shouldBeCulled;
	private float lifetime;
	private String name;
	private ArrayList<AbstractComponent> components;
	private ArrayList<String> tags;

	/**
	 * Create a new entity with a given name that should not be culled
	 * 
	 * @param name
	 */
	public Entity(String name) {
		this.shouldBeCulled = false;
		this.id = -1;
		this.name = name;
		components = new ArrayList<AbstractComponent>();

		tags = new ArrayList<>();
	}

	/**
	 * Create a new entity with a given name that should be culled
	 * 
	 * @param name
	 * @param culled
	 */
	public Entity(String name, boolean culled) {
		this.shouldBeCulled = culled;
		this.id = -1;
		this.name = name;
		components = new ArrayList<AbstractComponent>();

		tags = new ArrayList<>();
	}

	/**
	 * Get the entity name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Add a tag to the entity
	 * 
	 * @param tag
	 */
	public void addTag(String tag) {
		if (!tags.contains(tag)) {
			tags.add(tag);

		}
	}

	/**
	 * Remove the desired tag of the entity
	 *
	 * @param tag
	 */
	public void removeTag(String tag) {
		if(tags.contains(tag)) {
			tags.remove(tag);
		}
	}

	/**
	 * Return true if the entity has a tag
	 * 
	 * @param tag
	 * @return
	 */
	public boolean hasTag(String tag) {
		return tags.contains(tag);
	}

	/**
	 * Add a component to the entity
	 * 
	 * @param comp
	 */
	public void addComponent(AbstractComponent comp) {
		if (!hasComponent(comp.getClass())) {
			components.add(comp);
		}
	}

	/**
	 * Remove a component from the entity
	 * 
	 * @param comp
	 */
	public void removeComponent(AbstractComponent comp) {
		if (hasComponent(comp.getClass())) {
			components.remove(comp);
		}
	}

	/**
	 * Get all the component that the entity has
	 * 
	 * @return
	 */
	public ArrayList<AbstractComponent> getComponents() {
		return components;
	}

	/**
	 * Get a particular component from the enity
	 * 
	 * @param componentType
	 * @return
	 */
	public AbstractComponent getComponent(Class componentType) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getClass().equals(componentType)) {
				return components.get(i);
			}
		}
		return null;
	}

	/**
	 * Check to see if an entity has a component already
	 * 
	 * @param componentType
	 * @return
	 */
	public boolean hasComponent(Class componentType) {
		for (int i = 0; i < components.size(); i++) {
			if (components.get(i).getClass().equals(componentType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the entities unique id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the entities id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the entities transform component
	 * 
	 * @return
	 */
	public TransformComponent getTransform() {
		return (TransformComponent) getComponent(TransformComponent.class);
	}

	/**
	 * Get the entities sprite component
	 * 
	 * @return
	 */
	public SpriteComponent getSprite() {
		return (SpriteComponent) getComponent(SpriteComponent.class);
	}

	/**
	 * Get the entities network transform component
	 * 
	 * @return
	 */
	public NetTransformComponent getNetTransform() {
		return (NetTransformComponent) getComponent(NetTransformComponent.class);
	}

	/**
	 * Create a clone of the entity
	 */
	public Entity clone() {
		Entity result = new Entity(name);
		for (AbstractComponent comp : components) {
			result.addComponent(comp);
		}
		return result;
	}

	/**
	 * Return true if the entity should be called
	 * 
	 * @return
	 */
	public boolean shouldBeCulled() {
		return shouldBeCulled;
	}

	public void setLifeTime(float time) {
		lifetime = time;
	}

	public float getLifeTime() {
		return lifetime;
	}

	public void changeLifeTime(float time) {
		lifetime = time;
	}
}
