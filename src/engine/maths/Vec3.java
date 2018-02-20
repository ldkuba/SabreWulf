package engine.maths;

import java.io.Serializable;

public class Vec3 implements Serializable
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
	
	public Vec3(Vec3 other)
	{
		this.x = other.getX();
		this.y = other.getY();
		this.z = other.getZ();
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
	
	public void add(Vec3 other)
	{
		this.x += other.getX();
		this.y += other.getY();
		this.z += other.getZ();
	}
	
	public static Vec3 add(Vec3 v1, Vec3 v2)
	{
		return new Vec3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
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
		if(scale == 0.0f)
		{
			return new Vec3(0, 0, 0);
		}else
		{
			return new Vec3(vec.getX()/scale, vec.getY()/scale, vec.getZ()/scale);
		}
	}
}
