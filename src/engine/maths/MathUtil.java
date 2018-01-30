package engine.maths;

public class MathUtil
{
	public static Mat4 orthoProjMat(float bottom, float top, float left, float right, float near, float far)
	{
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

	public static Mat4 perspProjMat(float aspectRatio, float fov, float near, float far)
	{
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

	public static Mat4 simpleViewMat(Vec3 eye, Vec3 dir, Vec3 worldUp)
	{
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
//		view.setElement(3, position.getX() * -1.0f);
//		view.setElement(7, position.getY() * -1.0f);
//		view.setElement(11, position.getZ() * -1.0f);

		return view;
	}

	// Pitch should be in the range of [-90 ... 90] degrees and yaw
	// should be in the range of [0 ... 360] degrees.
	public static Mat4 fpsMat(Vec3 eye, float pitch, float yaw)
	{
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

	public static Mat4 lookAtMat(Vec3 eye, Vec3 location, Vec3 up)
	{
		Mat4 lookAt = new Mat4();

		return lookAt;
	}
}
