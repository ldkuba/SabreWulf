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
	private float x;
	private float y;

	private Sprite background;
	private Sprite progressBar;
	private Label label;

	public ProgressBar(float x, float y, float width, float height, Texture bgTexture, Texture barTexture, Font font, GUI gui) {
		this.gui = gui;
		this.x = x;
		this.y = y;
		maxProgres = 88.4f;
		progress = maxProgres;

		background = new Sprite(this.x, this.y, width, height, bgTexture);
		this.gui.add(background);

		progressBar = new Sprite(this.x, this.y, width, height, barTexture);
		this.gui.add(progressBar);
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
		resize(barWidth);
	}

	public float getProgress() {
		return progress;
	}

	public float getMaxProgres() {
		return maxProgres;
	}

	//delta in value
	public void changeBar(float delta) {
		progress += delta;
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth);
	}

	public void setBar(float progress)
	{
		progress = progress;
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth);
	}
	
	public void setPosition(float newX, float newY){
		//move the position of the bar
		this.background.x = newX;
		this.background.y = newY;
		this.progressBar.x = newX;
		this.progressBar.y = newY;
	}
}
