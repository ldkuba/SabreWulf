package game.player;

import engine.entity.Entity;

//Will contain an Entity object with components and holds data of it's own profile (health, status, position etc)
public class Player {
	
	private int id;
	private String name;
	private int[] currentPosition;
	private int health;
	private int energy;
	String status;
	
	public Entity player;
	
	public void createPlayer(int id, String name){
		this.id = id;
		this.name = name;
		
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
