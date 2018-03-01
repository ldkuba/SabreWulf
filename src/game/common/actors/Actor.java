package game.common.actors;

import engine.entity.Entity;
import game.common.inventory.Inventory;
import game.common.inventory.Item;

public class Actor
{

	public Actor()
	{
		entity = new Entity("Actor");
	}

	protected Inventory inventory;

	public void addItem(Item item)
	{

	}

	public void removeItem(Item item)
	{

	}

	public Inventory getInventory()
	{
		return inventory;
	}

	public void cleanInventory()
	{

	}

	protected Entity entity;

	public Entity getEntity()
	{
		return entity;
	}

	/**
	 * team can be 1, 2, 3 team 1 is composed of three players (first three in
	 * lobby) team 2 is composed of the other three players (last three in
	 * lobby) team 3 is composed of neutral npcs (shops, cart etc)
	 */

	protected int team;

	public int getTeam()
	{
		return team;
	}

	/**
	 * This will be affected by damage
	 */
	protected float health;

	public float getHealth()
	{
		return health;
	}

	public void setHealth(float health)
	{
		this.health = health;
	}

	/**
	 * This will be affected by casting spells
	 */
	protected float energy;

	public float getEnergy()
	{
		return energy;
	}

	public void setEnergy(float energy)
	{
		this.energy = energy;
	}

	/**
	 * This might be affected by items and root & snare spells
	 */
	protected float movementSpeed;

	public void setMovementSpeed(float movementSpeed)
	{
		this.movementSpeed = movementSpeed;
	}

	public float getMovementSpeed()
	{
		return movementSpeed;
	}

	/**
	 * This will be affected by items
	 */
	protected float resistance;

	public float getResistance()
	{
		return resistance;
	}

	public void setResistance(float resistance)
	{
		this.resistance = resistance;
	}

	/**
	 * 
	 * TODO
	 * 
	 * public Player(AbstractClasses role) { setStartingAttributes(); }
	 * 
	 * 
	 * private void setStartingAttributes() { health = role.getHealth(); =
	 * role.getStrength(); resistance = role.getDefence(); intelligence =
	 * role.getIntelligence(); }
	 */	
	
}
