package game.player;

import engine.entity.Entity;
import engine.entity.component.*;

//Will contain an Entity object with components and holds data of it's own profile (health, status, position etc)
public class Player {
	
	private int id;
	private String name;
	private int[] position;
	private int health;
	private int energy;
	String status;
	private boolean isLocal;
	
	public Entity player;
	public TransformComponent transform;
	
	public Player(int id, String name, boolean isLocal){
		this.id = id;
		this.name = name;
		this.isLocal = isLocal;
		
		player = new Entity(0, "Player " + name);
		transform = new TransformComponent();
		player.addComponent(transform);
	}

	public int[] getPosition() {
		return position;
	}

	public int getHealth() {
		return health;
	}

	public int getEnergy() {
		return energy;
	}

	public String getStatus() {
		return status;
	}
}
