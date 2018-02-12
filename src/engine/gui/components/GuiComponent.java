package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;

public class GuiComponent
{
	protected float x, y;
	protected float width, height;
	
	protected Entity entity;
	
	protected boolean enabled;
	
	public void onPress(int button)
	{
		
	}
	
	public void onRelease(int button)
	{
		
	}
	
	public void onRepeat(int button) 
	{
		
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getXAbsolute()
	{
		return x*Application.s_WindowSize.getX()/100.0f;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}
	
	public float getYAbsolute()
	{
		return y*Application.s_WindowSize.getY()/100.0f;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getWidth()
	{
		return width;
	}
	
	public float getWidthAbsolute()
	{
		return width*Application.s_WindowSize.getX()/100.0f;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}
	
	public float getHeightAbsolute()
	{
		return height*Application.s_WindowSize.getY()/100.0f;
	}

	public void setHeight(float height)
	{
		this.height = height;
	};	
}