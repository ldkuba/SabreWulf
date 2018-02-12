package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

public class Button extends GuiComponent
{	
	private Texture pressedTexture;
	private Texture releasedTexture;
	
	public Button(float x, float y, float width, float height, Texture pressed, Texture released)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		pressedTexture = pressed;
		releasedTexture = released;
		
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity = new Entity(0, "button");
		entity.addComponent(new TransformComponent());
		entity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), released, worldWidth, worldHeight));
	}
	
	//overridable for custom behaviour
	public void onClick()
	{
		//Implement Button bheaviou here
	}
	
	//internal
	@Override
	public void onPress(int button)
	{
		//change button Texture
		entity.getSprite().setTexture(pressedTexture);
		//callback
		onClick();
	}
	
	//internal
	@Override
	public void onRelease(int button)
	{
		//change button Texture
		entity.getSprite().setTexture(releasedTexture);
	}
	
	//internal
	@Override
	public void onRepeat(int button)
	{
		
	}
}
