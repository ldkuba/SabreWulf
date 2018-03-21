package game.common.logic.actions;

import java.io.Serializable;

import engine.application.Application;
import game.common.player.ActorManager;

/**
 * Abstract class where all actions will be made.
 *
 * @author SabreWulf
 */

public abstract class Action implements Serializable
{
	protected int sourceId;

	protected float cooldown;
	protected float cost;
	protected float maxCooldown;	
	
	/**
	 * Set source of attack
	 * @param sourceId
	 */
	public Action(int sourceId)
	{
		this.sourceId = sourceId;
	}
	
	public abstract void executeClient(ActorManager actorManager, Application app);
	public abstract void executeServer(ActorManager actorManager, Application app);

	public abstract boolean verify(ActorManager actorManager);

	/**
	 * Changes the cooldown for the attack by a given value
	 * @param time
	 */
	public void changeCooldown(float time)
	{
		cooldown -= time;
		
		if(cooldown < 0) 
			cooldown = 0;
	}
	
	/**
	 * Sets the cooldown for the attack
	 */
	public void setCooldown()
	{
		cooldown = maxCooldown;
	}
	
	/**
	 * Gets the ID of the attacker
	 * @return
	 */
	public int getSourceId()
	{
		return sourceId;
	}

	/**
	 * Gets the cooldown for the attack
	 * @return
	 */
	public float getCooldown() {
		return cooldown;
	}

	/**
	 * Gets the cost of the attack
	 * @return
	 */
	public float getCost() {
		return cost;
	}
}
