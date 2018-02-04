package engine.entity.component;

import engine.graphics.texture.Texture;
import engine.maths.Vec4;

public class SpriteComponent extends AbstractComponent
{
	private int m_Width, m_Height;
	private Texture m_Texture;
	private Vec4 m_Color;
	
	public SpriteComponent(Texture texture, Vec4 color)
	{
		
	}
	
	public SpriteComponent(Vec4 color)
	{
		
	}
}
