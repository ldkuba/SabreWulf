package game.common.actors;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.SpriteAnimationComponent;
import engine.maths.Vec3;

public class NPC {

	private ArrayList<Vec3> currentPath;
	protected Entity entity;
	protected Vec3 startPos;
	protected float movementSpeed;
	protected float healthLimit;
	protected float health;
	protected float damage;
	protected float attackRange;

	
	public NPC () {
		entity = new Entity("NPC");
		currentPath = new ArrayList<>();
	}

	public NPC(float health, float attackRange) {
		entity = new Entity("NPC");
		currentPath = new ArrayList<>();
		healthLimit = health;
		this.health = health;
		this.attackRange = attackRange;
	}
	
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	public float getMovementSpeed() {
		return movementSpeed;
	}

	public float getHealthLimit() {
		return healthLimit;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getHealth() {
		return health;
	}

	public void setDamage(float dmg) {
		this.damage = dmg;
	}

	public float getDamage() {
		return damage;
	}

	public void setAttackRange(float rng) {
		attackRange = rng;
	}

	public float getAttackRange() {
		return attackRange;
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

	public Vec3 getPosition() {
		return entity.getTransform().getPosition();
	}

	public void setPosition(Vec3 pos) {
		entity.getTransform().setPosition(pos);
	}
	
	public void update() {
		
	}
	 
}
