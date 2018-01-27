package engine.maths;

public class MathUtil
{	
	public static Mat4 orthoProjMat(float bottom, float top, float left, float right, float near, float far)
	{
		//initialises ortho to all 0.0f
		Mat4 ortho = new Mat4();
		
		ortho.setElement(0, 2 / (right - left));

		ortho.setElement(5, 2 / (top - bottom));

		ortho.setElement(10, -2 / (far - near));
		
		ortho.setElement(12, -(right + left) / (right - left));
		ortho.setElement(13, -(top + bottom) / (top - bottom));
		ortho.setElement(14, -(far + near) / (far - near));
		ortho.setElement(15, 1.0f);
		
		return ortho;
	}
	
	public static Mat4 simpleViewMat(Vec3 eye, Vec3 dir, Vec3 worldUp)
	{
		Mat4 view = Mat4.identity();
		
		Vec3 forward = Vec3.normalize(dir);
		Vec3 right = Vec3.crossProduct(Vec3.normalize(worldUp), forward);
		Vec3 up = Vec3.crossProduct(forward, right);
		
		eye.scale(-1.0f);
		
		view.setElement(0, right.getX());
		view.setElement(1, right.getY());
		view.setElement(2, right.getZ());
		view.setElement(3, Vec3.dotProduct(eye, right));
		
		view.setElement(4, forward.getX());
		view.setElement(5, forward.getY());
		view.setElement(6, forward.getZ());
		view.setElement(7, Vec3.dotProduct(eye, forward));
		
		view.setElement(8, up.getX());
		view.setElement(9, up.getY());
		view.setElement(10, up.getZ());
		view.setElement(11, Vec3.dotProduct(eye, up));	
		
		return view;
	}
	
	public static Mat4 lookAtMat(Vec3 eye, Vec3 location, Vec3 up)
	{
		Mat4 lookAt = new Mat4();
		
		
		
		return lookAt;
	}
}
