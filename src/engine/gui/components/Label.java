package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.font.Font;
import engine.maths.Vec4;

public class Label extends GuiComponent
{	
	private float size;
	private float spread;
	private TextComponent textComponent;
	
	public Label(float x, float y, Font font, float size, float spread, Vec4 color)
	{
		this.x = x;
		this.y = y;
		this.width = size*spread;
		this.height = size;
		this.size = size;
		this.spread = spread;
		this.enabled = true;
		
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity = new Entity("sprite");
		entity.addComponent(new TransformComponent());
		textComponent = new TextComponent(font, worldHeight, spread, color);
		entity.addComponent(textComponent);
	}
	
	@Override
	public void resize()
	{
		setSize(size);
	}
	
	public void setColor(Vec4 newColor)
	{
		textComponent.setColor(newColor);
	}
	
	public void setText(String text)
	{
		textComponent.setText(text);
		this.width = spread * size * text.length();
	}
	
	public void setSize(float size)
	{
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		textComponent.setSize(worldHeight);
		this.width = spread * size * textComponent.getText().length();
	}
	
	public void setSpread(float spread)
	{
		textComponent.setSpread(spread);
		this.width = spread * size * textComponent.getText().length();
	}
}
