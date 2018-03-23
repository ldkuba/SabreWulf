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

/**
 * Creates and manages the progress bars on the GUI
 * @author User
 *
 */
public class ProgressBar {

	private GUI gui;

	private Texture bgTexture;	//Dynamic Texture
	private Texture barTexture;	//Background texture
	private float progress;
	private float maxProgres;
	private float maxBarWidth;
	private float height;
	private float x;
	private float y;

	private Sprite background;
	private Sprite progressBar;
	private Label label;

	/**
	 * Creates a new progress bar at a given position, with the given dimensions and textures
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param bgTexture
	 * @param barTexture
	 * @param font
	 * @param gui
	 */
	public ProgressBar(float x, float y, float width, float height, Texture bgTexture, Texture barTexture, Font font, GUI gui) {
		this.gui = gui;
		this.x = x;
		this.y = y;
		maxBarWidth = width;
		maxProgres = 88.4f;
		progress = maxProgres;
		this.height = height;

		background = new Sprite(this.x, this.y, maxBarWidth, height, bgTexture);
		this.gui.add(background);

		progressBar = new Sprite(this.x, this.y, maxBarWidth, height, barTexture);
		this.gui.add(progressBar);
	}

	/**
	 * Resizes the progress bar when the window size gets resized
	 * @param barWidth
	 */
	private void resize(float barWidth) {
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

	/**
	 * Sets the maximum progress of the progress bar
	 * @param maxProgress
	 */
	//Affects the bartexture of the progress.
	public void setMaxProgress(float maxProgress) {
		this.maxProgres = maxProgress;
		float barWidth = (progress/maxProgress) * maxBarWidth;
		resize(barWidth);
	}

	/**
	 * Gets the current progress of the progress bar
	 * @return
	 */
	public float getProgress() {
		return progress;
	}

	/**
	 * Gets the maximum progress of the progress bar
	 * @return
	 */
	public float getMaxProgres() {
		return maxProgres;
	}

	/**
	 * Changes the progress and width of the progress bar by a given value
	 * @param delta
	 */
	//delta in value
	public void changeBar(float delta) {
		
		if(progress + delta < 0 || progress + delta > maxProgres)
		{
			return;
		}
		
		progress += delta;
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth);
	}

	/**
	 * Sets the width of the progress bar based on the given progress
	 * @param progress
	 */
	public void setBar(float progress)
	{
		if(progress < 0 || progress > maxProgres)
		{
			return;
		}
		
		this.progress = progress;
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth);
	}
	
	/**
	 * Sets the position of the progress bar on the map
	 * Used only for the progress bars indicating the statuses of the actors
	 * @param newX
	 * @param newY
	 * @param progress
	 * @param maxProgress
	 */
	public void setPosition(float newX, float newY, float progress, float maxProgress){
		
		this.progress = progress;
		this.maxProgres = maxProgress;
		
		//move the position of the bar
		this.background.setX(newX);
		this.background.setY(newY);
		this.progressBar.setX(newX);
		this.progressBar.setY(newY);
		
		background.setWidth(maxBarWidth / Application.s_Viewport.getX());
		background.setHeight(height / Application.s_Viewport.getY());
		background.resize();

		progressBar.setHeight(height / Application.s_Viewport.getY());
		float barWidth = (progress/maxProgres) * maxBarWidth;
		resize(barWidth / Application.s_Viewport.getX());
		progressBar.resize();
	}
}
