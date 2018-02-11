package engine.entity.component;

import engine.graphics.renderer.Renderable2D;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.texture.Texture;
import engine.maths.Mat4;
import engine.maths.Vec4;

public class SpriteComponent extends AbstractComponent
{
	private Renderable2D m_Sprite;
	
	public SpriteComponent(Vec4 color, Texture texture, float width, float height)
	{
		m_Sprite = new Renderable2D(width, height, color, texture);
	}
	
	public SpriteComponent(Vec4 color, float width, float height)
	{
		m_Sprite = new Renderable2D(width, height, color);
	}
	
	public void setTexture(Texture texture)
	{
		m_Sprite.setTexture(texture);
	}
	
	public void submit(Renderer2D renderer, Mat4 transformation)
	{
		renderer.submit(m_Sprite, transformation);
	}
}
