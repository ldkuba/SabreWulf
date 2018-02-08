package game.player;

import engine.entity.Entity;

//Will contain an Entity object with components and holds data of it's own profile (health, status, position etc)
public class Player {
	
	private int id;
	private String name;
	private int health;
	private int energy;
	String status;
	private boolean isLocal;
	
	public Entity player;
	
	public Player(int id, String name, boolean isLocal){
		this.id = id;
		this.name = name;
		this.isLocal = isLocal;
		
		player = new Entity(0, "Player " + name);
	}

	public void getPosition() {
		
	}

	public void getHealth() {
		
	}

	public void getEnergy() {
		
	}

	public void getStatus() {
		
	}
}
