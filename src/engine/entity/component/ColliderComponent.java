package engine.entity.component;

import engine.maths.Vec3;

public class ColliderComponent extends AbstractComponent{

	private enum ColliderType{
		CIRCLE_COLLIDER, BOX_COLLIDER, CYLINDER_COLLIDER
	}

	private boolean isTrigger;
	
	private ColliderType colliderType;

	//CIRCLE & CYLINDER
	private float radius;
	
	//BOX
	private Vec3 dimensions;
	
	//CYLINDER
	private float height;
	
	public ColliderComponent(float radius, boolean isTrigger) {
		this.colliderType = ColliderType.CIRCLE_COLLIDER;
		this.isTrigger = isTrigger;
		
		this.radius = radius;
	}
	
	//BOX
	public ColliderComponent(Vec3 dimensions, boolean isTrigger)
	{
		this.colliderType = ColliderType.CIRCLE_COLLIDER;
		this.isTrigger = isTrigger;
		
		this.dimensions = dimensions;
	}
	
	//CYLINDER
	public ColliderComponent(float radius, float height, boolean isTrigger)
	{
		this.colliderType = ColliderType.CYLINDER_COLLIDER;
		this.isTrigger = isTrigger;
		
		this.radius = radius;
		this.height = height;
	}
	
	public void update(){
		
	}

	public void onCollisionEnter() {
		
	}

	public void onCollisionStay() {

	}

	public void onCollisionExit() {

	}

	public void onTriggerEnter() {

	}
	
	public void onTriggerStay() {

	}
	
	public void onTriggerExit() {

	}
	
	public boolean isInBounds(Vec3 position, Vec3 colliderPos)
	{
		if(colliderType.compareTo(ColliderType.CIRCLE_COLLIDER) == 0)
		{
			position.setZ(colliderPos.getZ());
			
			if(Vec3.distance(position, colliderPos) <= radius)
			{
				return true;
			}
		}else if(colliderType.compareTo(ColliderType.BOX_COLLIDER) == 0)
		{
			
		}else if(colliderType.compareTo(ColliderType.CYLINDER_COLLIDER) == 0)
		{
			
		}
		
		return false;
	}
}
