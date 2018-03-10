package game.common.actors;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;

public class NPC {

	private ArrayList<Vec3> currentPath;
	protected Entity entity;
	protected Vec3 position;
	
	public NPC () {
		entity = new Entity("NPC");
		currentPath = new ArrayList<>();
	}
	
	protected float movementSpeed;
	
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public float getMovementSpeed() {
		return movementSpeed;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Vec3 getPosition() {
		return position;
	}
	
	public void setPosition(Vec3 position) {
		this.position = position;
	}
	
	public void update() {
		
	}
	 
}
