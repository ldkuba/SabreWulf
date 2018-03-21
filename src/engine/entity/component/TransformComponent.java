package engine.entity.component;

import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec3;

/**
 * Used to modify and create a transformation component for an entity
 * 
 * @author SabreWulf
 *
 */
public class TransformComponent extends AbstractComponent {
	private Vec3 position;
	private Vec3 eulerAngles;
	private Vec3 scale;

	/**
	 * Create a new transform component
	 */
	public TransformComponent() {
		position = new Vec3(0, 0, 0);
		eulerAngles = new Vec3(0, 0, 0);
		scale = new Vec3(1.0f, 1.0f, 1.0f);
	}

	/**
	 * Get the current position
	 * @return
	 */
	public Vec3 getPosition() {
		return new Vec3(position);
	}

	/**
	 * Set a new position
	 * @param position
	 */
	public void setPosition(Vec3 position) {
		this.position = position;
	}

	/**
	 * Get the Euler rotation angle
	 * @return
	 */
	public Vec3 getRotationAngles() {
		return new Vec3(eulerAngles);
	}

	/**
	 * Set the Euler rotation angle
	 * @param eulerAngles
	 */
	public void setRotationAngles(Vec3 eulerAngles) {
		this.eulerAngles = eulerAngles;
	}
	
	/**
	 * Get the scale
	 * @return
	 */
	public Vec3 getScale() {
		return new Vec3(scale);
	}

	/**
	 * Set the scale
	 * @param scale
	 */
	public void setScale(Vec3 scale) {
		this.scale = scale;
	}

	/**
	 * Move the position of the transform component
	 * @param movement
	 */
	public void move(Vec3 movement) {
		this.position = Vec3.add(this.position, movement);
	}

	public void moveLocal(Vec3 movement) {

	}

	/**
	 * Rotate the transform component by an angle
	 * @param angles
	 */
	public void rotate(Vec3 angles) {
		this.eulerAngles = Vec3.add(this.eulerAngles, angles);
	}

	/**
	 * Get the transformation matrix for the component - first scale then
	 * translate then rotate
	 * 
	 * @return
	 */
	public Mat4 getTransformationMatrix() {
		Mat4 scaleMat = MathUtil.createScaleMatrix(scale);
		Mat4 translationMat = MathUtil.createTranslationMatrix(position);
		Mat4 rotationMat = MathUtil.createRotationMatrix(eulerAngles);
		/* inverse order */
		return translationMat.mult(rotationMat).mult(scaleMat);
	}

	@Override
	public void update() {
	}
}
