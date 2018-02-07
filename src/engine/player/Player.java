package engine.player;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.AbstractComponent;

//Will contain an Entity object with components and holds data of it's own profile (health, status, position etc)
public class Player {
	
	private int id;
	private String name;
	private ArrayList<AbstractComponent> components;
	
	public Entity player;
	
	public void createPlayer(int id, String name){
		this.id = id;
		this.name = name;
		components = new ArrayList<AbstractComponent>();
		for(int i = 0; i < components.size(); i++){
			player.addComponent(components.get(i));
		}
	}
}
