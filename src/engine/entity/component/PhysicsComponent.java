package engine.entity.component;

public class PhysicsComponent {
	
	//TODO: Define different types of colliders, static, rigidbody etc...
	//TODO: Fill in these methods
	
	private float radius;
	private float distanceToObject;

	public PhysicsComponent(float radius) {
		this.radius = radius;
	}
	
	public void update(){
		if(distanceToObject < radius){
			onCollisionEnter();
			onTriggerEnter();
		}
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
}
