package engine.maths;

public class MathUtil {
	private static final double DEG2RAD = Math.PI / 180.0f;

	public static Mat4 orthoProjMat(float bottom, float top, float left, float right, float near, float far) {
		// initialises ortho to all 0.0f
		Mat4 ortho = new Mat4();

		ortho.setElement(0, 2 / (right - left));
		ortho.setElement(3, -(right + left) / (right - left));

		ortho.setElement(5, 2 / (top - bottom));
		ortho.setElement(7, -(top + bottom) / (top - bottom));

		ortho.setElement(10, -2 / (far - near));
		ortho.setElement(11, -(far + near) / (far - near));

		ortho.setElement(15, 1.0f);

		return ortho;
	}

	public static Mat4 perspProjMat(float aspectRatio, float fov, float near, float far) {
		Mat4 persp = new Mat4();

		float top = (float) (Math.tan(fov * 0.5 * Math.PI / 180.0f) * near);
		float bottom = -top;
		float right = top * aspectRatio;
		float left = -right;

		persp.setElement(0, (2.0f * near) / (right - left));
		persp.setElement(2, (right + left) / (right - left));
		persp.setElement(5, (2.0f * near) / (top - bottom));
		persp.setElement(6, (top + bottom) / (top - bottom));
		persp.setElement(10, -(far + near) / (far - near));
		persp.setElement(11, -(2.0f * far * near) / (far - near));
		persp.setElement(14, -1.0f);

		return persp;

	}

	public static Mat4 simpleViewMat(Vec3 eye, Vec3 dir, Vec3 worldUp) {
		Mat4 view = Mat4.identity();

		Vec3 forward = Vec3.normalize(dir);

		forward.scale(-1.0f);

		Vec3 right = Vec3.crossProduct(Vec3.normalize(worldUp), forward);
		Vec3 up = Vec3.crossProduct(forward, right);

		Vec3 position = new Vec3(-eye.getX(), -eye.getY(), -eye.getZ());

		view.setElement(0, right.getX());
		view.setElement(1, right.getY());
		view.setElement(2, right.getZ());
		view.setElement(3, Vec3.dotProduct(position, right));

		view.setElement(4, up.getX());
		view.setElement(5, up.getY());
		view.setElement(6, up.getZ());
		view.setElement(7, Vec3.dotProduct(position, up));

		view.setElement(8, forward.getX());
		view.setElement(9, forward.getY());
		view.setElement(10, forward.getZ());
		view.setElement(11, Vec3.dotProduct(position, forward));

		view.setElement(15, 1.0f);
		//
		// view.setElement(3, position.getX() * -1.0f);
		// view.setElement(7, position.getY() * -1.0f);
		// view.setElement(11, position.getZ() * -1.0f);

		return view;
	}

	// Pitch should be in the range of [-90 ... 90] degrees and yaw
	// should be in the range of [0 ... 360] degrees.
	public static Mat4 fpsMat(Vec3 eye, float pitch, float yaw) {
		// If the pitch and yaw angles are in degrees,
		// they need to be converted to radians. Here
		// I assume the values are already converted to radians.
		float cosPitch = (float) Math.cos(pitch);
		float sinPitch = (float) Math.sin(pitch);
		float cosYaw = (float) Math.cos(yaw);
		float sinYaw = (float) Math.sin(yaw);

		Vec3 xaxis = new Vec3(cosYaw, 0, -sinYaw);
		Vec3 yaxis = new Vec3(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
		Vec3 zaxis = new Vec3(sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw);

		// Create a 4x4 view matrix from the right, up, forward and eye position
		// vectors
		Mat4 view = new Mat4();

		view.setElement(0, xaxis.getX());
		view.setElement(1, xaxis.getY());
		view.setElement(2, xaxis.getZ());
		view.setElement(3, -1.0f * Vec3.dotProduct(xaxis, eye));

		view.setElement(4, yaxis.getX());
		view.setElement(5, yaxis.getY());
		view.setElement(6, yaxis.getZ());
		view.setElement(7, -1.0f * Vec3.dotProduct(yaxis, eye));

		view.setElement(8, zaxis.getX());
		view.setElement(9, zaxis.getY());
		view.setElement(10, zaxis.getZ());
		view.setElement(11, -1.0f * Vec3.dotProduct(zaxis, eye));

		view.setElement(15, 1.0f);

		return view;
	}

	public static Mat4 lookAtMat(Vec3 eye, Vec3 location, Vec3 up) {
		Mat4 lookAt = new Mat4();

		/*
		 * TODO
		 */

		return lookAt;
	}

	public static Mat4 createRotationMatrix(Vec3 angles) {
		Mat4 result = Mat4.identity();

		Mat4 rotationX = Mat4.identity();
		Mat4 rotationY = Mat4.identity();
		Mat4 rotationZ = Mat4.identity();

		rotationX.setElement(5, (float) Math.cos(angles.getX() * DEG2RAD));
		rotationX.setElement(6, (float) -Math.sin(angles.getX() * DEG2RAD));
		rotationX.setElement(9, (float) Math.sin(angles.getX() * DEG2RAD));
		rotationX.setElement(10, (float) Math.cos(angles.getX() * DEG2RAD));

		rotationY.setElement(0, (float) Math.cos(angles.getY() * DEG2RAD));
		rotationY.setElement(2, (float) Math.sin(angles.getY() * DEG2RAD));
		rotationY.setElement(8, (float) -Math.sin(angles.getY() * DEG2RAD));
		rotationY.setElement(10, (float) Math.cos(angles.getX() * DEG2RAD));

		rotationZ.setElement(0, (float) Math.cos(angles.getZ() * DEG2RAD));
		rotationZ.setElement(1, (float) -Math.sin(angles.getZ() * DEG2RAD));
		rotationZ.setElement(4, (float) Math.sin(angles.getZ() * DEG2RAD));
		rotationZ.setElement(5, (float) Math.cos(angles.getZ() * DEG2RAD));

		result = rotationZ.mult(rotationY).mult(rotationX);

		return result;
	}

	public static Mat4 createTranslationMatrix(Vec3 position) {
		Mat4 result = Mat4.identity();

		result.setElement(3, position.getX());
		result.setElement(7, position.getY());
		result.setElement(11, position.getZ());

		return result;
	}

	public static Mat4 createScaleMatrix(Vec3 scale) {
		Mat4 result = Mat4.identity();

		result.setElement(0, scale.getX());
		result.setElement(5, scale.getY());
		result.setElement(10, scale.getZ());

		return result;
	}

	public static void transformToUpperTriangle(double[][] matrix, int[] index) {
		double[] c;
		double c0, c1, pi0, pi1, pj;
		int itmp, k;

		c = new double[matrix.length];

		for (int i = 0; i < matrix.length; ++i) {
			index[i] = i;
		}

		for (int i = 0; i < matrix.length; ++i) {
			c1 = 0;

			for (int j = 0; j < matrix.length; ++j) {
				c0 = Math.abs(matrix[i][j]);

				if (c0 > c1) {
					c1 = c0;
				}
			}

			c[i] = c1;
		}

		k = 0;

		for (int j = 0; j < (matrix.length - 1); ++j) {
			pi1 = 0;

			for (int i = j; i < matrix.length; ++i) {
				pi0 = Math.abs(matrix[index[i]][j]);
				pi0 /= c[index[i]];

				if (pi0 > pi1) {
					pi1 = pi0;
					k = i;
				}
			}

			itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;

			for (int i = (j + 1); i < matrix.length; ++i) {
				pj = matrix[index[i]][j] / matrix[index[j]][j];
				matrix[index[i]][j] = pj;

				for (int l = (j + 1); l < matrix.length; ++l) {
					matrix[index[i]][l] -= pj * matrix[index[j]][l];
				}
			}
		}
	}

	public static double matrixDeterminant(double[][] matrix) {
		double temporary[][];
		double result = 0;

		if (matrix.length == 1) {
			result = matrix[0][0];
			return (result);
		}

		if (matrix.length == 2) {
			result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
			return (result);
		}

		for (int i = 0; i < matrix[0].length; i++) {
			temporary = new double[matrix.length - 1][matrix[0].length - 1];

			for (int j = 1; j < matrix.length; j++) {
				for (int k = 0; k < matrix[0].length; k++) {
					if (k < i) {
						temporary[j - 1][k] = matrix[j][k];
					} else if (k > i) {
						temporary[j - 1][k - 1] = matrix[j][k];
					}
				}
			}

			result += matrix[0][i] * Math.pow(-1, (double) i) * matrixDeterminant(temporary);
		}
		return (result);
	}

	public static double[][] invertMatrix(double[][] matrix) {
		double[][] auxiliaryMatrix, invertedMatrix;
		int[] index;

		auxiliaryMatrix = new double[matrix.length][matrix.length];
		invertedMatrix = new double[matrix.length][matrix.length];
		index = new int[matrix.length];

		for (int i = 0; i < matrix.length; ++i) {
			auxiliaryMatrix[i][i] = 1;
		}

		transformToUpperTriangle(matrix, index);

		for (int i = 0; i < (matrix.length - 1); ++i) {
			for (int j = (i + 1); j < matrix.length; ++j) {
				for (int k = 0; k < matrix.length; ++k) {
					auxiliaryMatrix[index[j]][k] -= matrix[index[j]][i] * auxiliaryMatrix[index[i]][k];
				}
			}
		}

		for (int i = 0; i < matrix.length; ++i) {
			invertedMatrix[matrix.length - 1][i] = (auxiliaryMatrix[index[matrix.length - 1]][i]
					/ matrix[index[matrix.length - 1]][matrix.length - 1]);

			for (int j = (matrix.length - 2); j >= 0; --j) {
				invertedMatrix[j][i] = auxiliaryMatrix[index[j]][i];

				for (int k = (j + 1); k < matrix.length; ++k) {
					invertedMatrix[j][i] -= (matrix[index[j]][k] * invertedMatrix[k][i]);
				}

				invertedMatrix[j][i] /= matrix[index[j]][j];
			}
		}

		return (invertedMatrix);
	}

	public static double[][] mat4ToDoubleArray(Mat4 matrix) {
		double[][] result = new double[4][4];
		float[] elements = matrix.getElements();

		for (int i = 0; i < 16; i++) {
			result[i % 16][i / 16] = (double) elements[i];
		}

		return result;
	}

	public static Mat4 doubleArrayToMat4(double[][] array) {
		Mat4 result = new Mat4();

		for (int i = 0; i < 16; i++) {
			result.setElement(i, (float) array[i % 16][i / 16]);
		}

		return result;
	}

	public Vec4 changeToSystem(Vec4 vector, Mat4 source, Mat4 target) {
		Vec4 intermediate = new Vec4();

		Mat4 inverse = doubleArrayToMat4(invertMatrix(mat4ToDoubleArray(source)));

		intermediate = vector.mult(inverse);

		Vec4 result = intermediate.mult(target);

		return result;
	}

	/**
	 * Converts a direction vector to a 4-way direction 
	 * 0 - up, 1 - left, 2 - down, 3 - right
	 * 
	 * @param direction
	 * @return
	 */
	public static int dirTo4Dir(Vec3 direction) {
		if (Math.abs(direction.getX()) >= Math.abs(direction.getY())) {
			if (direction.getX() >= 0) {
				return 3;
			} else {
				return 1;
			}
		} else {
			if (direction.getY() >= 0) {
				return 0;
			} else {
				return 2;
			}
		}
	}

	public static float dirToAngle(Vec3 direction)
	{
		if(direction.getY() >= 0)
		{
			return (float) Math.acos(direction.getX()/direction.getLength());
		}else
		{
			return (float) (2.0f * Math.PI - Math.acos(direction.getX() / direction.getLength()));
		}
	}
}
