package game.player;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;

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
	public SpriteComponent sprite;
	
	public Player(int id, String name, boolean isLocal, Texture texture){
		this.id = id;
		this.name = name;
		this.isLocal = isLocal;
		
		player = new Entity(0, "Player " + name);
		
		sprite = new SpriteComponent(null, texture, id, id);
		transform = new TransformComponent();
		player.addComponent(sprite);
		player.addComponent(transform);
	}

	public int[] getPosition() {
		return position;
	}
	
	public void setPosition(int[] pos){
		position = pos;
	}

	public int getHealth() {
		return health;
	}
	
	public void setHealth(int hp){
		health = hp;
	}

	public int getEnergy() {
		return energy;
	}
	
	public void setEnergy(int mana){
		energy = mana;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String newStatus){
		status = newStatus;
	}
	
	public boolean checkLocal(){
		return isLocal;
	}
	
	public int checkID(){
		return id; 
	}
	
	public void render()
	{
		
	}
}
