package game.common.customComponent;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.CustomComponent;
import engine.entity.component.NetIdentityComponent;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.net.common_net.networking_messages.RemoveEntityMessage;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.client.states.GameState;
import game.common.actors.Actor;
import game.server.states.ServerGameState;

public class HomingComponent extends CustomComponent
{
	private Actor target;
	private Entity ownerEntity;
	private float projectileSpeed;
	private Application app;
	
	private boolean hasHit;
	
	private final float MIN_DISTANCE = 0.2f;
	
	public HomingComponent(Application app, Entity owner, Actor target, float projectileSpeed)
	{
		this.ownerEntity = owner;
		this.target = target;
		this.projectileSpeed = projectileSpeed;
		this.app = app;
		this.hasHit = false;
	}
	
	@Override
	public void update()
	{
		if(!hasHit)
		{
			Vec3 moveDir = Vec3.subtract(target.getEntity().getNetTransform().getPosition(), ownerEntity.getNetTransform().getPosition());
			moveDir = Vec3.normalize(moveDir);
			moveDir.scale(projectileSpeed);
			
			ownerEntity.getNetTransform().setRotationAngles(new Vec3(0.0f, 0.0f, (float) Math.toDegrees(MathUtil.dirToAngle(moveDir))));
			
			ownerEntity.getNetTransform().move(moveDir);
			
			if(Vec3.subtract(ownerEntity.getNetTransform().getPosition(), target.getEntity().getNetTransform().getPosition()).getLength() < MIN_DISTANCE)
			{
				//callback
				onHit();
				
				hasHit = true;
				
				Scene scene = null;
				
				for(AbstractState state : app.getStateManager().getStates())
				{
					if(state instanceof GameState)
					{
						scene = state.getScene();
					}else if(state instanceof ServerGameState)
					{
						scene = state.getScene();
					}
				}
				
				int id = ((NetIdentityComponent) ownerEntity.getComponent(NetIdentityComponent.class)).getNetworkId();					
				
				app.getNetworkManager().deleteNetEntity(id, scene);
				
				RemoveEntityMessage rem = new RemoveEntityMessage();
				rem.setNetId(id);
				
				System.out.println("Removing Arrow: " + id);
				
				app.getNetworkManager().broadcast(rem);
			}
		}
	}
	
	public void onHit()
	{
		
	}
}
