package engine.gui.components;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.Vec4;

public class ProgressBar extends GuiComponent {

	Texture bgTexture;
	Texture barTexture;
	float progress;

	public ProgressBar(float x, float y, float width, float height, Texture bgTexture, Texture barTexture) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bgTexture = bgTexture;
		this.barTexture = barTexture;
		this.enabled = true;

		float worldWidth = (width * Application.s_WindowSize.getX() / 100.0f)
				* (Application.s_Viewport.getX() / (Application.s_WindowSize.getX() / 2.0f));
		float worldHeight = (height * Application.s_WindowSize.getY() / 100.0f)
				* (Application.s_Viewport.getY() / (Application.s_WindowSize.getY() / 2.0f));

		entity = new Entity(0, "progress bar");
		entity.addComponent(new TransformComponent());
		entity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), barTexture, worldWidth, worldHeight));
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public float getProgress() {
		return progress;
	}

}
