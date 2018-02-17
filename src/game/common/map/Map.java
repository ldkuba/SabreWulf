package game.common.map;

import java.util.ArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map {
	private ArrayList<Entity> entities;
	private ArrayList<Entity> visible;
	private Entity[] background;
	private final int MAP_SIZE_X = ((int) Application.s_WindowSize.getX()/10);
	private final int MAP_SIZE_Y = ((int) Application.s_WindowSize.getY()/10);
	private final float TILE_WIDTH = 1.0f;
	private final float TILE_HEIGHT = 1.0f;
	private final int MAP_SIZE = 10;
	private Scene scene;
	private float vpLength;

	public Map(Scene scene) {
		this.scene = scene;
		entities = new ArrayList<Entity>();
		visible = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE * 4];
		vpLength = Application.s_Viewport.getLength();
		init();
	}

	public void init() {
		// initialise entities - currently just test values
		Vec4 grey = new Vec4(0.4f, 0.4f, 0.4f, 1.0f);
		Vec4 pink = new Vec4(0.9f, 0.3f, 0.5f, 1.0f);
		Vec4 brown = new Vec4(0.4f, 0.1f, 0.0f, 1.0f);
		Vec4 blue = new Vec4(0.3f, 0.4f, 0.7f, 1.0f);
		Vec4 a = new Vec4(0.7f, 0.2f, 0.3f, 1.0f);
		Vec4 x = new Vec4(0.4f, 0.1f, 0.8f, 1.0f);

		// tr
		for (int i = 0; i < background.length / 4; i++) {
			Entity newEntity = new Entity(i, "");
			SpriteComponent comp1 = new SpriteComponent(brown, TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform()
					.setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 + TILE_HEIGHT * (i / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		// bl
		for (int i = 100; i < background.length / 2; i++) {
			SpriteComponent comp1 = null;
			Entity newEntity = new Entity(i, "");
			comp1 = new SpriteComponent(blue, TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(
					new Vec3(0 - TILE_WIDTH * ((i - 100) % MAP_SIZE), 0 - TILE_HEIGHT * ((i - 100) / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		// br
		for (int i = 200; i < 300; i++) {
			SpriteComponent comp1 = null;
			Entity newEntity = new Entity(i, "");
			comp1 = new SpriteComponent(pink, TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(
					new Vec3(0 + TILE_WIDTH * ((i - 200) % MAP_SIZE), 0 - TILE_HEIGHT * ((i - 200) / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		// tl
		for (int i = 300; i < background.length; i++) {
			SpriteComponent comp1 = null;
			Entity newEntity = new Entity(i, "");
			comp1 = new SpriteComponent(grey, TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(
					new Vec3(0 - TILE_WIDTH * ((i - 300) % MAP_SIZE), 0 + TILE_HEIGHT * ((i - 300) / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}

		for (int i = 0; i < background.length; i++) {
			scene.addEntity(background[i]);
		}
	}

	public void update() {
		float x = Application.s_Viewport.getX();
		float y = Application.s_Viewport.getY();
		float leftX = x - vpLength;
		float rightX = x + vpLength;
		float upperY = y + vpLength;
		float lowerY = y - vpLength;
		for (int i = 0; i < MAP_SIZE; i++) {
			Entity temp = background[i];
			Vec3 pos = temp.getTransform().getPosition();
			// assuming that pos gives the bottom
			float minX = pos.getX();
			float minY = pos.getY();
			float maxX = minX + temp.getSprite().getWidth();
			float maxY = minY + temp.getSprite().getHeight();
			if ((minX <= rightX && minX >= leftX) || (maxX <= rightX && maxX >= leftX)) {
				if (!visible.contains(temp)) {
					visible.add(temp);
				}
			}
			if ((minY <= upperY && minX >= lowerY) || (maxY <= upperY && maxY >= lowerY)) {
				if (!visible.contains(temp)) {
					visible.add(temp);
				}
			}
		}
		// remove entities that are no longer visible from the scene
		for (Entity entity : background) {
			if (!visible.contains(entity)) {
				scene.removeEntity(entity);
			}
		}
	}

	public ArrayList<Entity> getMapEntities() {
		return entities;
	}

	public ArrayList<Entity> getVisibleEntities() {
		return visible;
	}
}

