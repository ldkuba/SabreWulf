package engine.entity.component;

import engine.graphics.renderer.Renderer2D;
import engine.graphics.texture.Texture;
import engine.graphics.texture.TextureAtlas;
import engine.maths.Mat4;
import engine.maths.Vec4;

public class SpriteAnimationComponent extends AbstractComponent
{
	private SpriteComponent m_Sprite;
	private TextureAtlas m_TextureAtlas;
	private int m_StartFrame;
	private int m_EndFrame;
	private int m_CurrentFrame;
	
	private int m_TimeOfFrame;
	private int m_CurrentTime;
	
	public SpriteAnimationComponent(Texture texture, int framesPerRow, int firstIndex, int lastIndex, float width, float height, int speed)
	{
		m_StartFrame = firstIndex;
		m_EndFrame = lastIndex;
		m_CurrentFrame = firstIndex;
		
		m_CurrentTime = 0;
		
		m_TimeOfFrame = speed;
		
		m_TextureAtlas = new TextureAtlas(texture, framesPerRow, framesPerRow);
		m_Sprite = new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), m_TextureAtlas.getTexture(), width, height);			
	}
	
	public void update()
	{
		m_CurrentTime++;
		
		if(m_CurrentTime >= m_TimeOfFrame)
		{
			m_CurrentTime = 0;
			
			m_CurrentFrame++;
			
			if(m_CurrentFrame > m_EndFrame)
			{
				m_CurrentFrame = m_StartFrame;
				onEndPlayback();
			}
			
			m_Sprite.setUVs(m_TextureAtlas.getElementUVs(m_CurrentFrame));
		}
	}
	
	//callback when finished animating, overridable
	public void onEndPlayback()
	{
		
	}
	
	public void submit(Renderer2D renderer, Mat4 transformation)
	{
		m_Sprite.submit(renderer, transformation);
	}
}
