package engine.maths;

public class Vec4
{
	private float w,x,y,z;
	
	public Vec4()
	{
		this.w = 0.0f;
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public Vec4(float w, float x, float y, float z)
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec4(Vec3 v, float z)
	{
		this.w = v.getX();
		this.x = v.getY();
		this.y = v.getZ();
		this.z = z;
	}
	
	public float getW()
	{
		return this.w;
	}
	
	public void setW(float w)
	{
		this.w = w;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}	
	
	public Vec4 mult(Mat4 matrix)
	{
		Vec4 result = new Vec4();
		
		float[] elements = matrix.getElements();
		
		result.setW(elements[0] * w + elements[1] * x + elements[2] * y + elements[3] * z);
		result.setX(elements[4] * w + elements[5] * x + elements[6] * y + elements[7] * z);
		result.setY(elements[8] * w + elements[9] * x + elements[10] * y + elements[11] * z);
		result.setZ(elements[12] * w + elements[13] * x + elements[14] * y + elements[15] * z);
		
		return result;
	}
	
	public float[] elements()
	{
		float[] elements = new float[4];
		elements[0] = this.w;
		elements[1] = this.x;
		elements[2] = this.y;
		elements[3] = this.z;
		return elements;
	}
	
	public float[] elementsFlipped()
	{
		float[] elements = new float[4];
		elements[3] = this.w;
		elements[2] = this.x;
		elements[1] = this.y;
		elements[0] = this.z;
		return elements;
	}
}
