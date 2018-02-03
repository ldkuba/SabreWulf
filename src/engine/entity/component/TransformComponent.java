package engine.entity.component;

import engine.maths.Vec3;

public class TransformComponent extends AbstractComponent
{
	private Vec3 position;
	private Vec3 eulerAngles;
	private Vec3 scale;
	
	public TransformComponent()
	{
		position = new Vec3(0, 0, 0);
		eulerAngles = new Vec3(0, 0, 0);
		scale = new Vec3(1.0f, 1.0f, 1.0f);
	}

	public Vec3 getPosition()
	{
		return position;
	}

	public void setPosition(Vec3 position)
	{
		this.position = position;
	}

	public Vec3 getRotationAngles()
	{
		return eulerAngles;
	}

	public void setRotationAngles(Vec3 eulerAngles)
	{
		this.eulerAngles = eulerAngles;
	}

	public Vec3 getScale()
	{
		return scale;
	}

	public void setScale(Vec3 scale)
	{
		this.scale = scale;
	}
	
	/*
	 * TODO implement util functions like move, rotate, scale
	 */
	
}
