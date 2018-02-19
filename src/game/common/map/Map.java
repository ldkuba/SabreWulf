package game.common.map;

import engine.application.Application;
import engine.assets.AssetManager;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map {
	private Entity[] background;
	private final float TILE_WIDTH = 12f;
	private final float TILE_HEIGHT = 12f;
	private final int MAP_SIZE = 16;
	private final int ARRAY_SIZE = MAP_SIZE * MAP_SIZE;
	private Scene scene;
	private float vpLength;

	public Map(Scene scene) {
		this.scene = scene;
		background = new Entity[MAP_SIZE * MAP_SIZE];
		vpLength = Application.s_Viewport.getLength();
	}

	//only run on the client
	public void init(String basePath, AssetManager assetManager) {
		// initialise entities
		Vec4 white = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		// top right
		for (int i = 0; i < background.length; i++) {
			Entity newEntity = new Entity(i, "mapBackground" + i);
			SpriteComponent comp1 = new SpriteComponent(white, assetManager.getTexture(basePath + "/untitled_" + (i+1) + ".png"), TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * (i / MAP_SIZE), 1.0f));
			background[i] = newEntity;
		}
		
		for (int i = 0; i < background.length; i++) {
			scene.addEntity(background[i]);
		}
	}
}