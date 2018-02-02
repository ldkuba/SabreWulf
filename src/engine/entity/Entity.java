package engine.entity;

import java.util.ArrayList;

import engine.entity.component.AbstractComponent;

public class Entity {

	private int id;
	private String name;
	private ArrayList<AbstractComponent> components;

	public Entity(int id, String name) {
		this.id = id;
		this.name = name;
		components = new ArrayList<AbstractComponent>();
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

	public boolean hasComponent(Class componentType) {
		for (int i = 0; i < components.size(); i++) {
			AbstractComponent comp = components.get(i);
			if (comp.getClass().equals(componentType)) {
				return true;
			}
		}
		return false;
	}
}
