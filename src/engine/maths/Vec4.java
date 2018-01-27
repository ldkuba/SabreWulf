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
}
