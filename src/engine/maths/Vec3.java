package engine.maths;

public class Vec3
{
	private float x,y,z;
	
	public Vec3()
	{
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public Vec3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public float getLength()
	{
		return (float) Math.sqrt((double)(x*x + y*y + z*z));
	}
	
	public void scale(float scale)
	{
		x *= scale;
		y *= scale;
		z *= scale;
	}
	
	public static float dotProduct(Vec3 v1, Vec3 v2)
	{
		return (v1.getX()*v2.getX()) + (v1.getY()*v2.getY()) + (v1.getZ()*v2.getZ());
	}
	
	public static Vec3 crossProduct(Vec3 v1, Vec3 v2)
	{
		return new Vec3(v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
						v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
						v1.getX()*v2.getY() - v1.getY()*v2.getX());
	}
	
	public static Vec3 normalize(Vec3 vec)
	{
		float scale = vec.getLength();
		return new Vec3(vec.getX()/scale, vec.getY()/scale, vec.getZ()/scale);
	}
}
