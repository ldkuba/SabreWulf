package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.ProgressBarComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.font.Font;
import engine.graphics.texture.Texture;
import engine.maths.Vec3;
import engine.maths.Vec4;

import javax.xml.soap.Text;

public class ProgressBar extends GuiComponent {

	private Texture bgTexture;	//Dynamic Texture
	private Texture barTexture;	//Background texture
	private float progress;
	private float MAX_PROGRESS;
	private float m_Size;
	private float m_Spread;
	private float maxStringLength;

	private float barWidth;
	private float barHeight;

	TextComponent text;

	Vec4 color;

	public ProgressBar(float x, float y, float width, float height, Texture bgTexture, Texture barTexture, Font font) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		barWidth = width;
		barHeight = height;
		this.bgTexture = bgTexture;
		this.barTexture = barTexture;
		this.enabled = true;

		this.color = color;

		float worldWidth = (width * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f));
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));
		float worldBarWidth = (barWidth * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f));

		entity = new Entity("progress bar");
		entity.addComponent(new TransformComponent());
		TextComponent text = new TextComponent(font, worldHeight*0.07f, 0.7f, new Vec4(1.0f, 1.0f, 1.0f, 1.0f));
		m_Size = 0.5f;
		m_Spread = 0.7f;
		text.setText("100%");
		maxStringLength = 4;
		entity.addComponent(new ProgressBarComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), barTexture, worldBarWidth, worldHeight,5,5));
		entity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), bgTexture, worldWidth, worldHeight));
		entity.addComponent(text);

	}

	//Only used once in the beginning.
	public void initProgress(float progress) {
		MAX_PROGRESS = progress;
		this.progress = progress;
	}

	//Affects the bartexture of the progress.
	public void setProgress(float progress) {
		this.progress = progress;
	}

	public float getProgress() {
		return progress;
	}

	public String getProgressString() {
		float n = progress;
		return String.format("%.0f", n * 100) + "%";
	}

	//Zooming in and out
	@Override
	public void resize()
	{
		float worldWidth = (width*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));

		//Affect background
		entity.getSprite().setWidth(worldWidth);
		entity.getSprite().setHeight(worldHeight);

		//Affect Progress Bar
		ProgressBarComponent bar = (ProgressBarComponent) entity.getComponent(ProgressBarComponent.class);
		float worldBarWidth = (barWidth*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		bar.setWidth(worldBarWidth);
		bar.setHeight(worldHeight);
		System.out.println("Bar resized");
		System.out.println(bar.getWidth());

		//Affect Text
		TextComponent text = (TextComponent) entity.getComponent(TextComponent.class);
		//float worldHeight = (height*Application.s_WindowSize.getY()/100.0f) * (Application.s_Viewport.getY()/(Application.s_WindowSize.getY()/2.0f));
		text.setSize(worldHeight*0.07f);
	}

	public void changeBar(float reduce) {
		ProgressBarComponent bar = (ProgressBarComponent) entity.getComponent(ProgressBarComponent.class);
		 float curWidth = bar.getWidth();
		 System.out.println(curWidth);
		 System.out.println(bar.getWidth());
		 barWidth = curWidth - reduce;
		float worldBarWidth = (barWidth*Application.s_WindowSize.getX()/100.0f) * (Application.s_Viewport.getX()/(Application.s_WindowSize.getX()/2.0f));
		bar.setWidth(worldBarWidth);
		 System.out.println("Bar change");
	}

}
