package game.common.abilities.spells;

import engine.application.Application;
import game.client.Main;
import game.common.abilities.effects.LinkSprintEffect;
import game.common.logic.actions.Action;
import game.common.player.ActorManager;

public class LinkSprint extends Action
{	
	private final float duration = 3.0f;
	
	public LinkSprint(int sourceId)
	{
		super(sourceId);
		this.maxCooldown = 8.0f;
		this.cooldown = 0.0f;
		this.cost = 40.0f;
	}

	@Override
	public void executeClient(ActorManager actorManager, Application app)
	{
		
	}

	@Override
	public void executeServer(ActorManager actorManager, Application app)
	{
		LinkSprintEffect linkSprintEffect = new LinkSprintEffect(duration, actorManager.getActor(sourceId));
		actorManager.getActor(sourceId).getAbilities().get(0).setCooldown();
		actorManager.getActor(sourceId).setEnergy(actorManager.getActor(sourceId).getEnergy() - this.cost);
	}

	@Override
	public boolean verify(ActorManager actorManager)
	{
		if(actorManager.getActor(sourceId).getEnergy() < this.cost)
		{
			return false;
		}
		
		return true;
	}
	
	
}
