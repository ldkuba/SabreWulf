package game.common.customComponent;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.AbstractComponent;
import engine.maths.Vec3;
import game.common.actors.Actor;

public class HomingComponent extends AbstractComponent
{
	private Actor target;
	private Entity ownerEntity;
	private float projectileSpeed;
	private Application app;
	
	private final float MIN_DISTANCE = 0.2f;
	
	public HomingComponent(Application app, Entity owner, Actor target, float projectileSpeed)
	{
		this.ownerEntity = owner;
		this.target = target;
		this.projectileSpeed = projectileSpeed;
		this.app = app;
	}
	
	@Override
	public void update()
	{
		Vec3 moveDir = Vec3.subtract(target.getEntity().getNetTransform().getPosition(), ownerEntity.getNetTransform().getPosition());
		moveDir = Vec3.normalize(moveDir);
		moveDir.scale(projectileSpeed);
		
		ownerEntity.getNetTransform().move(moveDir);
		
		if(Vec3.subtract(ownerEntity.getNetTransform().getPosition(), target.getEntity().getNetTransform().getPosition()).getLength() < MIN_DISTANCE)
		{
			
		}
	}
}
