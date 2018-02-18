package engine.entity.component;

import java.util.ArrayList;

import engine.font.Font;
import engine.graphics.renderer.Renderer2D;
import engine.maths.Mat4;
import engine.maths.Vec4;

public class TextComponent extends AbstractComponent
{
	private String m_Text;
	
	private float m_Size;
	
	private Font m_Font;
	private ArrayList<SpriteComponent> m_Components;
	
	public TextComponent(Font font, float size)
	{
		m_Font = font;
		m_Components = new ArrayList<>();
		setText("");
	}
	
	public void setText(String text)
	{
		m_Text = text;
		
		//reconstruct arraylist
		m_Components = new ArrayList<>();
		
		for(int i = 0; i < text.length(); i++)
		{
			SpriteComponent component = new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), m_Font.getTexture(), m_Size, m_Size);
			component.setUVs(m_Font.getUVs(text.charAt(i)));
			m_Components.add(component);
		}		
	}
	
	public void setSize(float size)
	{
		m_Size = size;
		setText(m_Text);
	}
	
	public String getText()
	{
		return m_Text;
	}
	
	public void submit(Renderer2D renderer2d, Mat4 transformation)
	{
		for(SpriteComponent component : m_Components)
		{
			component.submit(renderer2d, transformation);
		}
	}
}
