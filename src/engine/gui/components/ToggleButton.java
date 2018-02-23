package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

public class ToggleButton extends GuiComponent
{
	private Texture selectedTexture;
	private Texture deselectedTexture;
	
	boolean toggled = false;
	
	public ToggleButton(float x, float y, float width, float height, Texture selected, Texture deselected)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = true;
		
		selectedTexture = selected;
		deselectedTexture = deselected;
		
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity = new Entity("button");
		entity.addComponent(new TransformComponent());
		entity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), deselected, worldWidth, worldHeight));
	}
	
	@Override
	public void resize()
	{
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity.getSprite().setWidth(worldWidth);
		entity.getSprite().setHeight(worldHeight);
	}
	
	//overridable for custom behaviour
	public void onClick(boolean toggled)
	{
		//Implement Button bheaviou here
	}
	
	//internal
	@Override
	public void onPress(int button)
	{
	}
	
	//internal
	@Override
	public void onRelease(int button)
	{
		//toggle texture
		if(toggled)
		{
			entity.getSprite().setTexture(deselectedTexture);
			toggled = false;
		}else
		{
			entity.getSprite().setTexture(selectedTexture);
			toggled = true;
		}
		//callback
		onClick(toggled);
	}
	
	//internal
	@Override
	public void onRepeat(int button)
	{
		
	}
	
	public void deselect()
	{
		entity.getSprite().setTexture(deselectedTexture);
		toggled = false;
	}
}
