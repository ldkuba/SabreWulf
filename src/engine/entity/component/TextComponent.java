package engine.entity.component;

import java.util.ArrayList;

import engine.font.Font;
import engine.graphics.renderer.Renderer2D;
import engine.maths.Vec3;
import engine.maths.Vec4;

public class TextComponent extends AbstractComponent
{
	private String m_Text;
	
	private float m_Size;
	
	private Font m_Font;
	private ArrayList<SpriteComponent> m_Components;
	
	private Vec4 m_Color;
	
	private float m_Spread;
	
	public TextComponent(Font font, float size, float spread, Vec4 color)
	{
		m_Font = font;
		m_Components = new ArrayList<>();
		m_Size = size;
		m_Spread = spread;
		m_Color = color;
		setText("");
	}
	
	public void setText(String text)
	{
		m_Text = text;
		
		//reconstruct arraylist
		m_Components = new ArrayList<>();
		
		for(int i = 0; i < text.length(); i++)
		{
			SpriteComponent component = new SpriteComponent(m_Color, m_Font.getTexture(), m_Size, m_Size);
			component.setUVs(m_Font.getUVs(text.charAt(i)));
			m_Components.add(component);
		}		
	}
	
	public void setColor(Vec4 newColor)
	{
		m_Color = newColor;
		setText(m_Text);
	}
	
	public void setSize(float size)
	{
		m_Size = size;
		setText(m_Text);
	}
	
	public void setSpread(float spread)
	{
		m_Spread = spread;
	}
	
	public String getText()
	{
		return m_Text;
	}
	
	public void submit(Renderer2D renderer2d, TransformComponent transform)
	{
		Vec3 tmpPos = transform.getPosition();
		Vec3 tmpRot = transform.getRotationAngles();
		Vec3 tmpScale = transform.getScale();
			
		transform.move(new Vec3(-m_Size*m_Spread*m_Components.size()/2.0f + (m_Size*m_Spread / 2.0f), 0.0f, 0.0f));
		
		for(SpriteComponent component : m_Components)
		{
			component.submit(renderer2d, transform.getTransformationMatrix());
			//later change to TransformComponent.moveLocal()
			transform.move(new Vec3(m_Size*m_Spread, 0, 0));
		}
		
		//reset the trasnform
		transform.setPosition(tmpPos);
		transform.setRotationAngles(tmpRot);
		transform.setScale(tmpScale);
	}

	@Override
	public void update()
	{		
	}
}
