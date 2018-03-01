package game.common.map;

import engine.application.Application;
import engine.assets.AssetManager;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map {

	private Entity[] background;
	private final float TILE_WIDTH = 12.0f;
	private final float TILE_HEIGHT = 12.0f;
	private final int MAP_SIZE = 16;
	private Scene scene;

	public Map(Scene scene) {
		this.scene = scene;
		background = new Entity[MAP_SIZE * MAP_SIZE];
	}

	// only run on the client
	public void init(String basePath, AssetManager assetManager) {
		// initialise entities
		Vec4 white = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);

		// bottom right
		for (int i = 0; i < background.length; i++) {
			Entity newEntity = new Entity("mapBackground" + i);
			SpriteComponent comp1 = new SpriteComponent(white,
					assetManager.getTexture(basePath + "/untitled_" + (i + 1) + ".png"), TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform()
					.setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * (i / MAP_SIZE), 1.0f));
			background[i] = newEntity;
		}

		for (int i = 0; i < background.length; i++) {
			scene.addEntity(background[i]);
		}
	}

	public void update() {
		for (int i = 0; i < background.length; i++) {
			if (isInView(background[i])) {
				scene.addEntity(background[i]);
			} else {
				if (scene.getEntities().contains(background[i])) {
					scene.removeEntity(background[i]);
				}
			}
		}
	}

	public boolean isInView(Entity entity) {
		/*
		 * p4____p2 | | |____| p1 p3
		 */
		Vec3 cam = scene.getCamera().getPosition();
		float view = Application.s_Viewport.getLength();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		if (sprite != null && transform != null) {
			float entWidth = sprite.getWidth();
			float entHeight = sprite.getHeight();
			Vec3 p1 = transform.getPosition();
			Vec2 p2 = new Vec2(p1.getX() + (entWidth), p1.getY() + (entHeight));
			Vec2 p3 = new Vec2(p1.getX() + entWidth, p1.getY());
			Vec2 p4 = new Vec2(p1.getX(), p1.getY() + entHeight);
			float xMinSpan = cam.getX() - (view / 2);
			float xMaxSpan = cam.getX() + (view / 2);
			float yMinSpan = cam.getY() - (view / 3);
			float yMaxSpan = cam.getY() + (view / 3);
			if (p4.getX() < xMaxSpan && p4.getX() > xMinSpan && p4.getY() < yMaxSpan && p4.getY() > yMinSpan) {
				return true;
			} else if (p3.getX() < xMaxSpan && p3.getX() > xMinSpan && p3.getY() < yMaxSpan && p3.getY() > yMinSpan) {
				return true;
			} else if (p2.getX() < xMaxSpan && p2.getX() > xMinSpan && p2.getY() < yMaxSpan && p2.getY() > yMinSpan) {
				return true;
			} else if (p1.getX() < xMaxSpan && p1.getX() > xMinSpan && p1.getY() < yMaxSpan && p1.getY() > yMinSpan) {
				return true;
			}
		}
		return false;
	}
}