package game.common.logic.actions;

import java.io.Serializable;

import game.common.actors.Actor;
import game.common.player.ActorManager;

public abstract class Action implements Serializable
{
	protected int sourceId;

	protected float cooldown;
	protected float cost;
	
	public Action(int sourceId)
	{
		this.sourceId = sourceId;
	}
	
	public abstract void executeClient(ActorManager actorManager);
	public abstract void executeServer(ActorManager actorManager);
	public abstract boolean verify(ActorManager actorManager);
	
	public int getSourceId()
	{
		return sourceId;
	}
}
