package game.map;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.graphics.camera.Camera;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map
{
	ArrayList<Entity> entities;
	Entity[] background;
	Camera camera;
	Entity shownEntity;
	ArrayList<Entity> hiddenEntities;
	Vec2 currentPos;
	int bkgrdPointer = 0;
	private final int MAP_SIZE = 10;
	Scene scene;

	public Map(Scene scene) {
		this.scene = scene;
		camera = scene.getCamera();
		entities = new ArrayList<Entity>();
		hiddenEntities = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE]; // map is 10 x 10
		// starting position of camera
		currentPos = new Vec2(camera.getViewport().getX(), camera.getViewport().getY());
	}

	public void init() {
		// initialise entities
		Vec4 colour1 =  new Vec4(2.0f, 3.0f, 1.0f, 0.0f);
		Vec4 colour2 =  new Vec4(1.0f, 2.0f, 3.0f, 0.0f);
		SpriteComponent comp1 = new SpriteComponent(colour1, 1.0f, 1.0f);
		SpriteComponent comp2 = new SpriteComponent(colour2, 1.0f, 1.0f);
	    for (int i = 0; i < background.length; i++){
			Entity newEntity = new Entity(i, "");
			if (i % 2 == 0){
				newEntity.addComponent(comp1);
			} else {
				newEntity.addComponent(comp2);
			}
			entities.add(newEntity);
			
	    }
		// set hidden and current shown entity
		for (Entity entity : entities) {
			hiddenEntities.add(entity);
		}
		bkgrdPointer = (int) (currentPos.getX() + ((float) MAP_SIZE * currentPos.getY()));
		shownEntity = background[bkgrdPointer];
	}

	public Entity getShownEntity() {
		// return the current visible map background
		return shownEntity;
	}
	
	public ArrayList<Entity> getMapEntities(){
		return entities;
	}
	
	public void render() {
		setCameraInBounds();
		// get new cameraPos
		Vec2 cameraPos = new Vec2(camera.getViewport().getX(), camera.getViewport().getY());
		float diffx = cameraPos.getX() - currentPos.getX();
		float diffy = cameraPos.getY() - currentPos.getY();
		/*
		 * if camera has moved left the x difference will be positive, moved up
		 * the y difference will be positive
		 */
		if (diffx > 0) {
			moveLeft(diffx);
		} else if (diffx < 0) {
			moveRight(diffx);
		}
		if (diffy > 0) {
			moveUp(diffy);
		} else if (diffy < 0) {
			moveDown(diffy);
		}
	}

	private void moveLeft(float diffx) {
		int steps = (int) diffx;
		bkgrdPointer = bkgrdPointer - steps;
		setBackground();
	}

	private void moveRight(float diffx) {
		int steps = (int) diffx;
		bkgrdPointer = bkgrdPointer + steps;
		setBackground();
	}

	private void moveUp(float diffy) {
		int steps = (int) diffy;
		bkgrdPointer = bkgrdPointer + -(steps * MAP_SIZE);
		setBackground();
	}

	private void moveDown(float diffy) {
		int steps = (int) diffy;
		bkgrdPointer = bkgrdPointer + (steps * MAP_SIZE);
		setBackground();
	}

	private void setBackground() {
		Entity temp;

		temp = background[bkgrdPointer];
		if (entities.contains(temp)) {
			if (temp != shownEntity) {
				hiddenEntities.remove(temp);
				if(shownEntity != null)
				{
					hiddenEntities.add(shownEntity);
				}
				shownEntity = temp;
			}
		}
	}

	public void setCameraInBounds()
	{
		// if camera goes out of map bounds bring it back in.
		boolean changePos = false;

		Vec3 position = new Vec3(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ());
		if (position.getX() > (float) MAP_SIZE / 2) {
			position.setX((float) MAP_SIZE / 2);
			changePos = true;
		} else if (position.getX() < -(MAP_SIZE / 2)) {
			position.setX(-(MAP_SIZE / 2));
			changePos = true;
		}

		if (position.getY() > (float) MAP_SIZE / 2) {
			position.setY((float) MAP_SIZE / 2);
			changePos = true;

		} else if (position.getY() < -(MAP_SIZE / 2)) {
			position.setY(-(MAP_SIZE / 2));
			changePos = true;
		}

		if (position.getZ() > (float) MAP_SIZE / 2) {
			position.setZ((float) MAP_SIZE / 2);
			changePos = true;

		} else if (position.getZ() < -(MAP_SIZE / 2)) {
			position.setZ(-(MAP_SIZE / 2));
			changePos = true;
		}

		if (changePos) {
			camera.move(position);
		}
	}
}
