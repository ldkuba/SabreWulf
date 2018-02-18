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
	private ArrayList<Entity> visible;
	private Entity[] background;
	private final float TILE_WIDTH = 24.0f;
	private final float TILE_HEIGHT = 24.0f;
	private final int MAP_SIZE = 10;
	private final int ARRAY_SIZE = MAP_SIZE * MAP_SIZE * 4;
	private Scene scene;
	private float vpLength;

	private float oldcamx;
	private float oldcamy;
	
	public Map(Scene scene) {
		this.scene = scene;
		visible = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE * 4];
		vpLength = Application.s_Viewport.getLength();
	}

	public void init() {
		// initialise entities - currently just test values
		Vec4 grey = new Vec4(0.4f, 0.4f, 0.4f, 1.0f);
		Vec4 pink = new Vec4(0.9f, 0.3f, 0.5f, 1.0f);
		Vec4 brown = new Vec4(0.4f, 0.1f, 0.0f, 1.0f);
		Vec4 blue = new Vec4(0.3f, 0.4f, 0.7f, 1.0f);
		Vec4[] colors = new Vec4[4];
		colors[0] = grey;
		colors[1] = pink;
		colors[2] = brown;
		colors[3] = blue;
		// top right
		for (int i = 0; i < background.length / 4; i++) {
			Entity newEntity = new Entity(i, "");
			SpriteComponent comp1 = new SpriteComponent(colors[i%4], TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 + TILE_HEIGHT * (i / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		// bottom left
		for (int i = (ARRAY_SIZE/4); i < background.length / 2; i++) {
			SpriteComponent comp1 = new SpriteComponent(colors[i%4], TILE_WIDTH, TILE_HEIGHT);;
			Entity newEntity = new Entity(i, "");
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(new Vec3(0 - TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * ((i - 100) / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		// bottom right
		for (int i = (ARRAY_SIZE/2); i < (background.length*3/4); i++) {
			SpriteComponent comp1 = new SpriteComponent(colors[i%4], TILE_WIDTH, TILE_HEIGHT);;
			Entity newEntity = new Entity(i, "");
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * ((i - 200) / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		// top left
		for (int i = (ARRAY_SIZE*3/4); i < background.length; i++) {
			SpriteComponent comp1 = new SpriteComponent(colors[i%4], TILE_WIDTH, TILE_HEIGHT);;
			Entity newEntity = new Entity(i, "");
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform().setPosition(new Vec3(0 - TILE_WIDTH * (i % MAP_SIZE), 0 + TILE_HEIGHT * ((i - 300) / MAP_SIZE), 0.0f));
			background[i] = newEntity;
		}
		for (int i = 0; i < background.length; i++) {
			scene.addEntity(background[i]);
			visible.add(background[i]);
		}

		oldcamx = scene.getCamera().getPosition().getX();
		oldcamy = scene.getCamera().getPosition().getY();
	}

	/*public void update() {
		clearVisibleEntities();
		float x = Application.s_Viewport.getX();
		float y = Application.s_Viewport.getY();
		System.out.println("View Port");
		System.out.println(x);
		System.out.println(y);
		
		float camX = scene.getCamera().getPosition().getX();
		float camY = scene.getCamera().getPosition().getY();
		
		float xchange = camX - oldcamx;
		float ychange = camY - oldcamy;
		System.out.println("xchange " + xchange);
		System.out.println("ychange " + ychange);
		System.out.println("vp length " + vpLength);
		float leftX = x + xchange;
		float rightX = x + vpLength + xchange;
		float upperY = y + vpLength + ychange;
		float lowerY = y + ychange;
		System.out.println("visible scope of VP");
		System.out.println("LEFT X " + leftX);
		System.out.println("RIGHT X " + rightX);
		System.out.println(upperY);
		System.out.println(lowerY);
		for (int i = 0; i < background.length; i++) {
			Entity temp = background[i];
			Vec3 pos = temp.getTransform().getPosition();
			// pos gives the bottom left of entity
			float minX = pos.getX(); 
			float minY = pos.getY(); 
			float maxX = minX + temp.getSprite().getWidth();
			float maxY = minY + temp.getSprite().getHeight();
			System.out.println(i);
			System.out.println("min x " + minX);
			System.out.println("min y " + minY);
			System.out.println("max x " + maxX);
			System.out.println("max y " + maxY);
			if (((minX <= rightX && minX >= leftX) || (maxX <= rightX && maxX >= leftX)) && ((minY <= upperY && minX >= lowerY) || (maxY <= upperY && maxY >= lowerY))){
				if (!visible.contains(temp)) {
					visible.add(temp);
				}
			} 
		}
		// remove entities that are no longer visible from the scene
		//remove all entities
		scene.removeAllEnties();
		for (Entity entity : background) {
			if (visible.contains(entity)) {
				scene.addEntity(entity);
			}
		}
		oldcamx += xchange;
		oldcamy += ychange;
		System.out.println("Showing map elements: " + visible.size());
	}*/
	
	/*private void clearVisibleEntities(){
		if(!visible.isEmpty()){
			visible.clear();
			System.out.println("clear enities - should be zero/null " + visible.size());
		}
	}*/

	public ArrayList<Entity> getVisibleEntities() {
		return visible;
	}
}