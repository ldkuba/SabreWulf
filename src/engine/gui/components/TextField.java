package engine.gui.components;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.font.Font;
import engine.maths.Vec4;

public class TextField extends GuiComponent
{
	private float size;
	private float spread;
	private TextComponent textComponent;
	
	private int maxStringLength;
	
	public TextField(float x, float y, Font font, float size, float spread, int maxStringLength)
	{
		this.x = x;
		this.y = y;
		this.width = size*spread*maxStringLength/2.0f;
		this.height = size;
		this.size = size;
		this.spread = spread;
		this.enabled = true;
		this.maxStringLength = maxStringLength;
		
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		
		entity = new Entity(0, "sprite");
		entity.addComponent(new TransformComponent());
		entity.addComponent(new SpriteComponent(new Vec4(0.8f, 0.8f, 0.8f, 1.0f), worldWidth, worldHeight));
		textComponent = new TextComponent(font, worldHeight, spread);
		entity.addComponent(textComponent);
	}
	
	public void setText(String text)
	{
		textComponent.setText(text);
	}
	
	public void addChar(int key)
	{
		String current = textComponent.getText();
		
		if(current.length() >= maxStringLength)
		{
			return;
		}
		
		if(key == 32 || key == 39 || (key >= 44 && key <= 57) || key == 59 || key == 61 || (key >= 65 && key <= 93) || key == 96)
		{
			current += String.valueOf((char) (key));
		}else
		{
			return;
		}
		
		setText(current);
	}
	
	public void removeChar()
	{
		String current = textComponent.getText();
		
		if(current.length() == 0)
		{
			return;
		}
		
		current = current.substring(0, current.length() - 1);
		
		setText(current);
	}
	
	@Override
	public void onKeyPress(int key, int action)
	{
		if(action == GLFW.GLFW_PRESS)
		{			
			if(key == GLFW.GLFW_KEY_BACKSPACE)
			{
				removeChar();
			}else
			{
				addChar(key);
			}
		}
	}
	
	public void setSize(float size)
	{
		textComponent.setSize(size);
		this.width = spread * size * maxStringLength;
	}
	
	public void setSpread(float spread)
	{
		textComponent.setSpread(spread);
		this.width = spread * size * maxStringLength;
	}

	public String getText()
	{
		return textComponent.getText();
	}
}
