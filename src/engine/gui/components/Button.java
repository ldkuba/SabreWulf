package engine.gui.components;

import engine.graphics.texture.Texture;

public class Button extends GuiComponent
{	
	private Texture pressedTexture;
	private Texture releasedTexture;
	
	public Button(int x, int y, int width, int height, Texture pressed, Texture released)
	{
		pressedTexture = pressed;
		releasedTexture = released;
	}
	
	//overridable for custom behaviour
	public void onClick()
	{
		
	}
	
	//internal
	public void onPress()
	{
		
	}
	
	//internal
	public void onRelease()
	{
		
	}
}
