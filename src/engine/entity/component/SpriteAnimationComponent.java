package engine.entity.component;

import java.util.ArrayList;

import engine.graphics.renderer.Renderable2D;
import engine.graphics.texture.Texture;
import engine.graphics.texture.TextureAtlas;

public class SpriteAnimationComponent extends AbstractComponent
{
	private ArrayList<Renderable2D> m_Frames;
	private TextureAtlas m_TextureAtlas;
	private int[] m_FrameIndices;
	
	private int m_FramesPerImage;
	
	public SpriteAnimationComponent(TextureAtlas textureAtlas, int firstIndex, int lastIndex)
	{
		m_FrameIndices = new int[lastIndex-firstIndex];
		
	}
}
