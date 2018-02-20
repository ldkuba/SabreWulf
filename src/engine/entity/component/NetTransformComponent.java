package engine.entity.component;

import java.io.Serializable;
import java.util.ArrayList;

import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec3;

public class NetTransformComponent extends AbstractComponent
{
	private Vec3 position;
	private Vec3 eulerAngles;
	private Vec3 scale;
	
	public NetTransformComponent()
	{
		position = new Vec3(0, 0, 0);
		eulerAngles = new Vec3(0, 0, 0);
		scale = new Vec3(1.0f, 1.0f, 1.0f);
	}
	
	public ArrayList<Serializable> getData()
	{
		ArrayList<Serializable> data = new ArrayList<>();
		data.add(position);
		data.add(eulerAngles);
		data.add(scale);
		return data;
	}

	public Vec3 getPosition()
	{
		return new Vec3(position);
	}

	public void setPosition(Vec3 position)
	{
		this.position = position;
	}

	public Vec3 getRotationAngles()
	{
		return new Vec3(eulerAngles);
	}

	public void setRotationAngles(Vec3 eulerAngles)
	{
		this.eulerAngles = eulerAngles;
	}

	public Vec3 getScale()
	{
		return new Vec3(scale);
	}

	public void setScale(Vec3 scale)
	{
		this.scale = scale;
	}
	
	public void move(Vec3 movement)
	{
		this.position = Vec3.add(this.position, movement);
	}
	
	public void moveLocal(Vec3 movement)
	{
		
	}
	
	public void rotate(Vec3 angles)
	{
		this.eulerAngles = Vec3.add(this.eulerAngles, angles);
	}
	
	//first scale then translate then rotate
	public Mat4 getTransformationMatrix()
	{
		Mat4 scaleMat = MathUtil.createScaleMatrix(scale);
		Mat4 translationMat = MathUtil.createTranslationMatrix(position);
		Mat4 rotationMat = MathUtil.createRotationMatrix(eulerAngles);
		
		//inverse order
		return translationMat.mult(rotationMat).mult(scaleMat);
	}	
}
