package game.map;

import java.util.ArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map {
	private ArrayList<Entity> entities;
	private ArrayList<Entity> visible;
	private Entity[] background;
	private final int MAP_SIZE = 10;
	private Scene scene;
	private float vpLength;

	public Map(Scene scene) {
		this.scene = scene;
		entities = new ArrayList<Entity>();
		visible = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE];
		vpLength = Application.s_Viewport.getLength();
	}

	public void init() {
		// initialise entities - currently just test values
		Vec4 colour1 = new Vec4(1.0f, 1.0f, 1.0f, 0.0f);
		Vec4 colour2 = new Vec4(0.0f, 0.0f, 0.0f, 0.0f);
		SpriteComponent comp1 = new SpriteComponent(colour1, 1.0f, 1.0f);
		SpriteComponent comp2 = new SpriteComponent(colour2, 1.0f, 1.0f);
		for (int i = 0; i < background.length; i++) {
			Entity newEntity = new Entity(i, "");
			if (i % 2 == 0) {
				newEntity.addComponent(comp1);
			} else {
				newEntity.addComponent(comp2);
			}
			background[i] = newEntity;
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
			System.out.println(temp);
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