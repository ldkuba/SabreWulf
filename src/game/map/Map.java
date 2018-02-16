package game.map;

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
	private final int MAP_SIZE = 10;
	private final float TILE_WIDTH = 1.0f;
	private final float TILE_HEIGHT = 1.0f;
	private Scene scene;
	private float vpLength;

	public Map(Scene scene) {
		this.scene = scene;
		entities = new ArrayList<Entity>();
		visible = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE];
		vpLength = Application.s_Viewport.getLength();
		init();
	}

	public void init() {
		// initialise entities - currently just test values
		Vec4 colour1 = new Vec4(0.4f, 0.4f, 0.4f, 1.0f);
		Vec4 colour2 = new Vec4(0.9f, 0.3f, 0.5f, 1.0f);
		
		for (int i = 0; i < background.length; i++) {
			Entity newEntity = new Entity(i, "");
			
			if (i % 2 == 0 || i % 7 == 0) {
				SpriteComponent comp1 = new SpriteComponent(colour1, TILE_WIDTH, TILE_HEIGHT);
				newEntity.addComponent(comp1);
			} else {
				SpriteComponent comp2 = new SpriteComponent(colour2, TILE_WIDTH, TILE_HEIGHT);
				newEntity.addComponent(comp2);
			}
			
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(new Vec3(0 + TILE_WIDTH*(i%MAP_SIZE), 0 + TILE_HEIGHT*(i/MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		for (int i = 0; i < background.length; i++){
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
			if (!visible.contains(entity)){
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