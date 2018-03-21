package engine.maths;

import java.io.Serializable;

/**
 * 2D vector class that would be used by the game and engine
 * @author SabreWulf
 *
 */
public class Vec2 implements Serializable {
	private float x, y;

	/**
	 * Creates a 2D vector without coordinate values
	 */
	public Vec2() {
		this.x = 0.0f;
		this.y = 0.0f;
	}

	/**
	 * Creates a 2D vector with coordinate values
	 * @param x
	 * @param y
	 */
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the X coordinate of the 2D vector
	 * @return
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the X coordinate of the 2D vector
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
	 * Gets the length of the 2D vector
	 * @return
	 */
	public float getLength() {
		return (float) Math.sqrt((double) (x * x + y * y));
	}

	/**
	 * Scales the coordinates of the 2D vector by a given scale
	 * @param scale
	 */
	public void scale(float scale) {
		x *= scale;
		y *= scale;
	}

	/**
	 * Adds two 2D vectors
	 * @param other
	 */
	public void add(Vec2 other) {
		this.x += other.getX();
		this.y += other.getY();
	}
	
	/**
	 * Adds three 2D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec2 add(Vec2 v1, Vec2 v2) {
		return new Vec2(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}
	
	/**
	 * Subtracts three 2D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec2 subtract(Vec2 v1, Vec2 v2) {
		return new Vec2(v1.getX() - v2.getX(), v1.getY() - v2.getY());
	}

	/**
	 * Calculates the dot product of two 2D vectors and returns the result
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static float dotProduct(Vec2 v1, Vec2 v2) {
		return (v1.getX() * v2.getX()) + (v1.getY() * v2.getY());
	}

	/**
	 * Normalizes a given 2D vector and return its normalized form
	 * @param vec
	 * @return
	 */
	public static Vec2 normalize(Vec2 vec) {
		float scale = vec.getLength();
		if (scale == 0.0f) {
			return new Vec2(0, 0);
		} else {
			return new Vec2(vec.getX() / scale, vec.getY() / scale);
		}
	}
}
