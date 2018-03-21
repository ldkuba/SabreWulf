package engine.maths;

import java.io.Serializable;

/**
 * 3D vector class that would be used by the game and engine
 * @author SabreWulf
 *
 */
public class Vec3 implements Serializable {
	private float x,y,z;

	/**
	 * Creates a 3D vector without coordinate values
	 */
	public Vec3() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}

	/**
	 * Creates a 3D vector with coordinate values
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a 3D vector from another 3D vector
	 * @param other
	 */
	public Vec3(Vec3 other) {
		this.x = other.getX();
		this.y = other.getY();
		this.z = other.getZ();
	}

	/**
	 * Gets the value of the X coordinate
	 * @return
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the X coordinate
	 * @param x
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the value of the Y coordinate
	 * @return
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the Y coordinate
	 * @param y
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Gets the value of the Z coordinate
	 * @return
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the value of the Z coordinate
	 * @param z
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Gets the length of the 3D vector
	 * @return
	 */
	public float getLength() {
		return (float) Math.sqrt((double)(x*x + y*y + z*z));
	}
	
	/**
	 * Scales the coordinates of the 2D vector by a given scale
	 * @param scale
	 */
	public void scale(float scale) {
		x *= scale;
		y *= scale;
		z *= scale;
	}

	/**
	 * Adds two 3D vectors
	 * @param other
	 */
	public void add(Vec3 other) {
		this.x += other.getX();
		this.y += other.getY();
		this.z += other.getZ();
	}

	/**
	 * Adds three 3D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec3 add(Vec3 v1, Vec3 v2) {
		return new Vec3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
	}

	/**
	 * Calculates the dot product of two 3D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float dotProduct(Vec3 v1, Vec3 v2) {
		return (v1.getX()*v2.getX()) + (v1.getY()*v2.getY()) + (v1.getZ()*v2.getZ());
	}

	/**
	 * Calculates the cross product of two 3D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec3 crossProduct(Vec3 v1, Vec3 v2) {
		return new Vec3(v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
				v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
				v1.getX()*v2.getY() - v1.getY()*v2.getX());
	}

	/**
	 * Stores the coordinates in an array as elements in the order of X, Y, Z and returns the array
	 * @return
	 */
	public float[] elements() {
		float[] elements = new float[3];
		elements[0] = this.x;
		elements[1] = this.y;
		elements[2] = this.z;
		return elements;
	}

	/**
	 * Flips the elements in the array and returns the array
	 * Coordinates are now stored in the order of Z, Y, X
	 * @return
	 */
	public float[] elementsFlipped() {
		float[] elements = new float[3];
		elements[2] = this.x;
		elements[1] = this.y;
		elements[0] = this.z;
		return elements;
	}

	/**
	 * Normalizes a given 3D vector and return its normalized form
	 * @param vec
	 * @return
	 */
	public static Vec3 normalize(Vec3 vec) {
		float scale = vec.getLength();
		if(scale == 0.0f) {
			return new Vec3(0, 0, 0);
		} else {
			return new Vec3(vec.getX()/scale, vec.getY()/scale, vec.getZ()/scale);
		}
	}
	
	/**
	 * Subtracts two 3D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec3 subtract(Vec3 v1, Vec3 v2) {
		return new Vec3(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
	}

	/**
	 * Calculates the distance between two 3D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float distance(Vec3 v1, Vec3 v2) {
		return (float) Math.sqrt((v1.getX() - v2.getX())*(v1.getX() - v2.getX()) + (v1.getY() - v2.getY())*(v1.getY() - v2.getY()));
	}
}

