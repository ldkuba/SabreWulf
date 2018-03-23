package game.common.customComponent;

import engine.entity.Entity;
import engine.entity.component.CustomComponent;
import game.common.player.ActorManager;

public class CapturePointComponent extends CustomComponent
{
	private ActorManager actorManager;
	private Entity entity;
	
	public CapturePointComponent(ActorManager actorManager, Entity entity)
	{
		this.actorManager = actorManager;
		this.entity = entity;
	}

	@Override
	public void update()
	{
		//CApturing points
	}
}
