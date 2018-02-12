package engine.maths;

public class Vec2
{
	private float x,y;
	
	public Vec2()
	{
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Vec2(float x, float y)
	{
		this.x = x;
		this.y = y;
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
	
	public float getLength()
	{
		return (float) Math.sqrt((double)(x*x + y*y));
	}
	
	public void scale(float scale)
	{
		x *= scale;
		y *= scale;
	}
	
	public void add(Vec2 other)
	{
		this.x += other.getX();
		this.y += other.getY();
	}
	
	public static Vec2 add(Vec2 v1, Vec2 v2)
	{
		return new Vec2(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}
	
	public static float dotProduct(Vec2 v1, Vec2 v2)
	{
		return (v1.getX()*v2.getX()) + (v1.getY()*v2.getY());
	}
	
	public static Vec2 normalize(Vec2 vec)
	{
		float scale = vec.getLength();
		if(scale == 0.0f)
		{
			return new Vec2(0, 0);
		}else
		{
			return new Vec2(vec.getX()/scale, vec.getY()/scale);
		}
	}
}
