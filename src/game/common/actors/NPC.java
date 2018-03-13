package game.common.actors;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;

public class NPC {

	private ArrayList<Vec3> currentPath;
	protected Entity entity;
	protected Vec3 startPos;
	protected float movementSpeed;
	
	public NPC () {
		entity = new Entity("NPC");
		currentPath = new ArrayList<>();
	}
	
	
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public float getMovementSpeed() {
		return movementSpeed;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Vec3 getStartPosition() {
		return startPos;
	}
	
	public void setStartPosition(Vec3 position) {
		this.startPos = position;
	}
	
	public void update() {
		
	}
	 
}
