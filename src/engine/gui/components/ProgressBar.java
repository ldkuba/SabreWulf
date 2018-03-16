package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.font.Font;
import engine.graphics.texture.Texture;
import engine.gui.GUI;
import engine.maths.Vec2;
import engine.maths.Vec4;

public class ProgressBar {

	private GUI gui;

	private Texture bgTexture;	//Dynamic Texture
	private Texture barTexture;	//Background texture
	private float progress;
	private float maxProgres;
	private float maxBarWidth;

	private Sprite background;
	private Sprite progressBar;
	private Label label;

	public ProgressBar(float x, float y, float width, float height, Texture bgTexture, Texture barTexture, Font font, GUI gui) {
		this.gui = gui;
		maxProgres = 88.4f;
		progress = maxProgres;
		maxBarWidth = width;

		background = new Sprite(x, y, width, height, bgTexture);
		gui.add(background);

		progressBar = new Sprite(x, y, width, height, barTexture);
		gui.add(progressBar);

		label = new Label(x,y+1.5f,font,3.0f,0.6f, new Vec4(1.0f,1.0f,1.0f,1.0f));
		label.setText(""+progress+"/"+maxProgres);
		gui.add(label);
	}

	private void resize(float barWidth)
	{
		progressBar.setWidth(barWidth);

		float progresRatio = progress/maxProgres;

		Vec2[] uvs = {
			new Vec2(0, 0),
			new Vec2(0, 1),
			new Vec2(progresRatio, 1),
			new Vec2(progresRatio, 0)
		};

		progressBar.setUVs(uvs);

		progressBar.resize();
	}

	//Affects the bartexture of the progress.
	public void setMaxProgress(float maxProgress) {
		this.maxProgres = maxProgress;
		float barWidth = (progress/maxProgress) * maxBarWidth;
		System.out.println("WIDTH: "+ barWidth);
		resize(barWidth);
		label.setText(""+progress+"/"+maxProgres);
	}

	public float getProgress() {
		return progress;
	}

	public float getMaxProgres() {
		return maxProgres;
	}

	//delta in value
	public void changeBar(float delta) {
		if(progress + delta > maxProgres || progress + delta < 0)
		{
			return;
		}
		System.out.println(progress);
		progress += delta;
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth);
		label.setText(""+progress+"/"+maxProgres);
	}

	public void setBar(float progress)
	{
		this.progress = progress;
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth);
		label.setText(""+progress+"/"+maxProgres);
	}
}
