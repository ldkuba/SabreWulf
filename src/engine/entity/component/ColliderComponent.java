package engine.entity.component;

import engine.maths.Vec3;

/**
 * Allows you to create a component that acts will cause a player to have a
 * collision when walked into
 * 
 * @author SabreWulf
 *
 */
public class ColliderComponent extends AbstractComponent {

	/**
	 * Set up custom enums used in the Collider Component class
	 * 
	 * @author SabreWulf
	 *
	 */
	private enum ColliderType {
		CIRCLE_COLLIDER, BOX_COLLIDER, CYLINDER_COLLIDER
	}

	private boolean isTrigger;
	private ColliderType colliderType;
	/* Circle and Cylinder */
	private float radius;
	/* Box */
	private Vec3 dimensions;
	/* Cylinder */
	private float height;

	public ColliderComponent(float radius, boolean isTrigger) {
		this.colliderType = ColliderType.CIRCLE_COLLIDER;
		this.isTrigger = isTrigger;

		this.radius = radius;
	}

	/**
	 * Create a collider component for a box
	 * 
	 * @param dimensions
	 * @param isTrigger
	 */
	public ColliderComponent(Vec3 dimensions, boolean isTrigger) {
		this.colliderType = ColliderType.CIRCLE_COLLIDER;
		this.isTrigger = isTrigger;

		this.dimensions = dimensions;
	}

	/**
	 * Create a collider for a cylinder
	 * 
	 * @param radius
	 * @param height
	 * @param isTrigger
	 */
	public ColliderComponent(float radius, float height, boolean isTrigger) {
		this.colliderType = ColliderType.CYLINDER_COLLIDER;
		this.isTrigger = isTrigger;

		this.radius = radius;
		this.height = height;
	}

	@Override
	public void update() {

	}

	/**
	 * 
	 */
	public void onCollisionEnter() {

	}

	/**
	 * 
	 */
	public void onCollisionStay() {

	}

	/**
	 * 
	 */
	public void onCollisionExit() {

	}

	/**
	 * 
	 */
	public void onTriggerEnter() {

	}

	/**
	 * 
	 */
	public void onTriggerStay() {

	}

	/**
	 * 
	 */
	public void onTriggerExit() {

	}

	/**
	 * Check the collider is in bounds
	 * 
	 * @param position
	 * @param colliderPos
	 * @return
	 */
	public boolean isInBounds(Vec3 position, Vec3 colliderPos) {
		if (colliderType.compareTo(ColliderType.CIRCLE_COLLIDER) == 0) {
			position.setZ(colliderPos.getZ());

			if (Vec3.distance(position, colliderPos) <= radius) {
				return true;
			}
		} else if (colliderType.compareTo(ColliderType.BOX_COLLIDER) == 0) {

		} else if (colliderType.compareTo(ColliderType.CYLINDER_COLLIDER) == 0) {

		}

		return false;
	}
}
