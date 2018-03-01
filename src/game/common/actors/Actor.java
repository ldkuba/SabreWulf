package game.common.actors;

import java.util.ArrayList;
import java.util.LinkedList;

import engine.entity.Entity;
import engine.maths.Vec2;
import game.common.classes.AbstractClasses;
import game.common.inventory.Inventory;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.*;
import game.common.logic.ActorLogic;

public class Actor {

	public Actor() {
		entity = new Entity("Actor");
	}

	protected Inventory inventory;

	public void addItem(Item item) {
		//Affect the actors stats.
		addItemAttributes(item);
		inventory.addItem(item);
	}
	
	//Affect the stats of the player.
	private void addItemAttributes(Item item) {
		LinkedList<Attribute> attributes = item.getAttributes();
		
		for(int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			if (attribute instanceof Damage) {
				float newDamage = getDamage() + attribute.getValue();
				setDamage(newDamage);
			} else if (attribute instanceof Energy) {
				float newEnergy = getEnergyLimit() + attribute.getValue();
				setEnergy(newEnergy);
			} else if (attribute instanceof Health) {
				float newHealth = getHealthLimit() + attribute.getValue();
				setHealth(newHealth);
			} else if (attribute instanceof MovementSpeed) {
				float newMoveSpeed = getMovementSpeed() + attribute.getValue();
				setMovementSpeed(newMoveSpeed);
			} else if (attribute instanceof Resistance) {
				float newResis = getResistance() + attribute.getValue();
				setResistance(newResis);
			}
		}
		
	}

	public void removeItem(Item item) {
		//Affect the actors stats.
		remItemAttributes(item);
		inventory.rmvItem(item);
	}
	
	//Affect the players stats once the item has been removed.
	private void remItemAttributes(Item item) {
		LinkedList<Attribute> attributes = item.getAttributes();
		
		for(int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			if (attribute instanceof Damage) {
				float newDamage = getDamage() - attribute.getValue();
				setDamage(newDamage);
			} else if (attribute instanceof Energy) {
				float newEnergy = getEnergyLimit() - attribute.getValue();
				setEnergy(newEnergy);
			} else if (attribute instanceof Health) {
				float newHealth = getHealthLimit() - attribute.getValue();
				setHealth(newHealth);
			} else if (attribute instanceof MovementSpeed) {
				float newMoveSpeed = getMovementSpeed() - attribute.getValue();
				setMovementSpeed(newMoveSpeed);
			} else if (attribute instanceof Resistance) {
				float newResis = getResistance() - attribute.getValue();
				setResistance(newResis);
			}
		}
		
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void cleanInventory() {
		inventory.clear();
	}

	protected Entity entity;

	public Entity getEntity() {
		return entity;
	}

	/**
	 * team can be 1, 2, 3 team 1 is composed of three players (first three in
	 * lobby) team 2 is composed of the other three players (last three in
	 * lobby) team 3 is composed of neutral npcs (shops, cart etc)
	 */

	protected int team;

	public int getTeam() {
		return team;
	}

	protected Vec2 base;

	public Vec2 getBase() {
		return base;
	}

	public void setBase(Vec2 base) {
		this.base = base;
	}

	protected Vec2 position;

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	/**
	 * This will be affected by damage
	 */
	protected float HEALTH_LIMIT;
	
	public void setHealthLimit(float health) {
		HEALTH_LIMIT = health;
	}
	
	public float getHealthLimit() {
		return HEALTH_LIMIT;
	}
	
	protected float health;

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	/**
	 * This will be affected by casting spells
	 */
	protected float ENERGY_LIMIT;
	
	public float getEnergyLimit() {
		return ENERGY_LIMIT;
	}
	
	public void setEnergyLimit(float lim) {
		ENERGY_LIMIT = lim;
	}
	
	protected float energy;

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	/**
	 * This might be affected by items and root & snare spells
	 */
	protected float movementSpeed;

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	/**
	 * This will be affected by items
	 */
	protected float resistance;

	public float getResistance() {
		return resistance;
	}

	public void setResistance(float resistance) {
		this.resistance = resistance;
	}

	/**
<<<<<<< HEAD
	 * This will be affected by items.
	 */

	protected float damage;

	public float getDamage() {
		return damage;
	}

	public void setDamage(float dmg) {
		damage = dmg;
	}

	/**
	 * This will remain permanent for now.
	 */

	protected float attackRange;

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float rng) {
		attackRange = rng;
	}

	protected ActorLogic logic;

	public ActorLogic getLogic() {
		return logic;
	}

	/**
	 * Only used once.
	 */

	private AbstractClasses role;

	public void setPlayer(AbstractClasses role) {
		health = role.getHealth();
		resistance = role.getResistance();
		movementSpeed = role.getMoveSpeed();
		energy = role.getEnergy();
		damage = role.getDamage();
		this.role = role;
	}

}
