package game.common.logic.actions;

import java.io.Serializable;

import game.common.actors.Actor;
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
	
	/**
	 * Abstract methods for executing the attack on the client and server side
	 * Verify method to verify that the attacker can attack its target
	 * @param actorManager
	 */
	public abstract void executeClient(ActorManager actorManager);
	public abstract void executeServer(ActorManager actorManager);
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
