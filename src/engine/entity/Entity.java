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
		if (componentExists(comp) == false) {
			components.add(comp);
		}
	}

	public void removeComponent(AbstractComponent comp) {
		if (componentExists(comp) == true) {
			components.remove(comp);
		}
	}

	public boolean componentExists(AbstractComponent comp) {
		if (components.contains(comp)) {
			return true;
		}
		return false;
	}

	public ArrayList<AbstractComponent> getClassesComponents() {
		return components;
	}

	public boolean hasComponent(Class<AbstractComponent> componentType) {
		for (int i = 0; i < components.size(); i++) {
			AbstractComponent comp = components.get(i);
			if (comp.getClass().equals(componentType)) {
				return true;
			}
		}
		return false;
	}
}
