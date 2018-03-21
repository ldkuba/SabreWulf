package game.common.logic.actions;

import java.io.Serializable;

import game.common.actors.Actor;
import game.common.player.ActorManager;

/**
 * Abstract class where all actions will be made.
 *
 * @author Sabrewulf
 */

public abstract class Action implements Serializable
{
	protected int sourceId;

	protected float cooldown;
	protected float cost;
	protected float maxCooldown;	
	
	public Action(int sourceId)
	{
		this.sourceId = sourceId;
	}
	
	public abstract void executeClient(ActorManager actorManager);
	public abstract void executeServer(ActorManager actorManager);
	public abstract boolean verify(ActorManager actorManager);

	public void changeCooldown(float time)
	{
		cooldown -= time;
		
		if(cooldown < 0) 
			cooldown = 0;
	}
	
	public void setCooldown()
	{
		cooldown = maxCooldown;
	}
	
	public int getSourceId()
	{
		return sourceId;
	}

	public float getCooldown() {
		return cooldown;
	}

	public float getCost() {
		return cost;
	}
}
