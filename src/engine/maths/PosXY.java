package engine.maths;

/**
 * Get the x and y position
 * 
 * @author SabreWulf
 *
 */
public class PosXY {
	int x, y;

	/**
	 * Create a new position
	 * 
	 * @param x
	 * @param y
	 */
	PosXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get X
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get Y
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}
}
