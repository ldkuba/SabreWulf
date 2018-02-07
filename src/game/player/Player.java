package game.player;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.AbstractComponent;

//Will contain an Entity object with components and holds data of it's own profile (health, status, position etc)
public class Player {
	
	private int id;
	private String name;
	
	public Entity player;
	
	public void createPlayer(int id, String name){
		this.id = id;
		this.name = name;
		
	}
}
