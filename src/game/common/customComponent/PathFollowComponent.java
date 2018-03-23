package game.common.customComponent;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.CustomComponent;
import engine.maths.Vec3;

public class PathFollowComponent extends CustomComponent
{
	private final float MIN_DISTANCE = 0.2f;
	
	private ArrayList<Vec3> path;
	private int currentPoint;
	
	private Entity entity;
	
	private float speed = 0.1f;
	
	public PathFollowComponent(ArrayList<Vec3> path, Entity entity)
	{
		this.path = path;
		this.currentPoint = 1;
		this.entity = entity;
	}

	@Override
	public void update()
	{
		Vec3 moveDir = Vec3.subtract(entity.getTransform().getPosition(), path.get(currentPoint));
		
		if(moveDir.getLength() < MIN_DISTANCE)
		{
			entity.getTransform().setPosition(path.get(currentPoint));
			
			currentPoint++;
			
			if(currentPoint >= path.size())
			{
				entity.getTransform().setPosition(path.get(0));
				currentPoint = 1;
			}
		}else
		{
			moveDir = Vec3.normalize(moveDir);
			moveDir.scale(speed);
			
			entity.getTransform().move(moveDir);
		}
	}
}
