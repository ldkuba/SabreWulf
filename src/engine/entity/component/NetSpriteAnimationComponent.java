package engine.entity.component;

import java.io.Serializable;

import engine.graphics.renderer.Renderer2D;
import engine.maths.Mat4;
import engine.net.common_net.Synchronizable;

public class NetSpriteAnimationComponent extends AbstractComponent implements Serializable
{
	private int m_StartFrame;
	private int m_EndFrame;
	private int m_CurrentFrame;

	private int m_TimeOfFrame;
	private int m_CurrentTime;

	public NetSpriteAnimationComponent(int firstIndex, int lastIndex, int speed)
	{
		m_StartFrame = firstIndex;
		m_EndFrame = lastIndex;
		m_CurrentFrame = firstIndex;

		m_CurrentTime = 0;

		m_TimeOfFrame = speed;
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
		}
	}

	public void changeAnimationFrames(int firstIndex, int lastIndex)
	{
		m_StartFrame = firstIndex;
		m_EndFrame = lastIndex;
		m_CurrentFrame = firstIndex;
		
		
	}

	public void stopAnimation()
	{
		m_CurrentFrame = m_StartFrame;
		m_EndFrame = m_StartFrame;
	}

	// callback when finished animating, overridable
	public void onEndPlayback()
	{

	}

	public int getStartFrame()
	{
		return m_StartFrame;
	}

	public int getEndFrame()
	{
		return m_EndFrame;
	}

	public int getCurrentFrame()
	{
		return m_CurrentFrame;
	}

	public int getTimeOfFrame()
	{
		return m_TimeOfFrame;
	}

	public int getCurrentTime()
	{
		return m_CurrentTime;
	}
}