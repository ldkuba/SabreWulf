package engine.maths;

/**
 * Four-vector class that would be used by the game and engine
 * @author SabreWulf
 *
 */
public class Vec4 {
	private float w,x,y,z;
	
	/**
	 * Creates a four-vector without coordinate values
	 */
	public Vec4() {
		this.w = 0.0f;
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	/**
	 * Creates a four-vector with coordinate values
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vec4(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates a four-vector from a 3D vector and a z coordinate value
	 * @param v
	 * @param z
	 */
	public Vec4(Vec3 v, float z) {
		this.w = v.getX();
		this.x = v.getY();
		this.y = v.getZ();
		this.z = z;
	}
	
	/**
	 * Gets the value of the W coordinate
	 * @return
	 */
	public float getW() {
		return this.w;
	}
	
	/**
	 * Sets the value of the W coordinate
	 * @param w
	 */
	public void setW(float w) {
		this.w = w;
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
	 * Calculates a vector-matrix product and returns the result of the calculation
	 * @param matrix
	 * @return
	 */
	public Vec4 mult(Mat4 matrix) {
		Vec4 result = new Vec4();
		
		float[] elements = matrix.getElements();
		
		result.setW(elements[0] * w + elements[1] * x + elements[2] * y + elements[3] * z);
		result.setX(elements[4] * w + elements[5] * x + elements[6] * y + elements[7] * z);
		result.setY(elements[8] * w + elements[9] * x + elements[10] * y + elements[11] * z);
		result.setZ(elements[12] * w + elements[13] * x + elements[14] * y + elements[15] * z);
		
		return result;
	}
	
	/**
	 * Stores the coordinates in an array as elements in the order of W, X, Y, Z and returns the array
	 * @return
	 */
	public float[] elements() {
		float[] elements = new float[4];
		elements[0] = this.w;
		elements[1] = this.x;
		elements[2] = this.y;
		elements[3] = this.z;
		return elements;
	}
	
	/**
	 * Flips the elements in the array and returns the array
	 * Coordinates are now stored in the order of Z, Y, X, W
	 * @return
	 */
	public float[] elementsFlipped() {
		float[] elements = new float[4];
		elements[3] = this.w;
		elements[2] = this.x;
		elements[1] = this.y;
		elements[0] = this.z;
		return elements;
	}
}
