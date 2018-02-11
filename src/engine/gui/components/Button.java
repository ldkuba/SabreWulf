package engine.gui.components;

import engine.entity.Entity;
import engine.graphics.texture.Texture;

public class Button extends GuiComponent
{	
	private Texture pressedTexture;
	private Texture releasedTexture;
	
	public Button(int x, int y, int width, int height, Texture pressed, Texture released)
	{
		pressedTexture = pressed;
		releasedTexture = released;
		
		entity = new Entity(0, "button");
				
		
	}
	
	//overridable for custom behaviour
	public void onClick()
	{
		//Implement Button bheaviou here
	}
	
	//internal
	public void onPress()
	{
		//change button Texture
		
		
		
		//callback
		onClick();
	}
	
	//internal
	public void onRelease()
	{
		
	}
	
	//internal
	public void onRepeat()
	{
		
	}
}
