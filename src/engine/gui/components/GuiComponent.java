package engine.gui.components;

import engine.entity.Entity;

public class GuiComponent
{
	protected int x, y;
	protected int width, height;
	
	protected Entity entity;
	
	public void onPress(int button)
	{
		
	}
	
	public void onRelease(int button)
	{
		
	}
	
	public void onRepeat(int button) 
	{
		
	}

	
	public Entity getEntity()
	{
		return entity;
	}
	
	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	};	
}