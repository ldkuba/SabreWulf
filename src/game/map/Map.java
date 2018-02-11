package game.map;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.graphics.camera.Camera;
import engine.maths.Vec3;

public class Map {
	ArrayList<Entity> entities;
	Entity[] background;
	Camera camera;
	Entity shownEntity;
	ArrayList<Entity> hiddenEntities;
	Vec3 currentPos;
	int i = 0; 
	private final int MAP_SIZE = 10;

	public Map() {
		entities = new ArrayList<Entity>();
		hiddenEntities = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE]; // map is 10 x 10
		//starting position of camera 
		currentPos = new Vec3(getCameraPosX(), getCameraPosY(), getCameraPosZ());
	}
	
	public void init() {
		// initialise entities ???
		
		//set hidden and current shown entity
		for (Entity entity : entities) {
			hiddenEntities.add(entity);
		}
		shownEntity = background[i];
	}
	
	public Entity getShownEntity() {
		//return the current visible map background
		return shownEntity;
	}
	
	public void render(){
		//get new cameraPos
		Vec3 cameraPos = new Vec3(getCameraPosX(), getCameraPosY(), getCameraPosZ());
		float diffx = cameraPos.getX() - currentPos.getX();
		float diffy = cameraPos.getY() - currentPos.getY();
		float diffz = cameraPos.getZ() - currentPos.getZ();
		/* if camera has moved left the x difference will be positive
		*                moved up the y difference will be positive
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

	private void moveLeft(float diffx){
		int steps = (int) diffx;
		i = i - steps;
		setBackground();
	}
	
	private void moveRight(float diffx){
		int steps = (int) diffx;
		i = i + steps;
		setBackground();
	}
	
	private void moveUp(float diffy){
		int steps = (int) diffy;
		i = i + -(steps * MAP_SIZE);
		setBackground();
	}
	
	private void moveDown(float diffy){
		int steps = (int) diffy;
		i = i + (steps * MAP_SIZE);
		setBackground();
	}
	
	private void setBackground(){
		Entity temp;
		int i = (int) (currentPos.getX() + ((float) MAP_SIZE * currentPos.getY()));
		temp = background[i];
		if (entities.contains(temp)) {
			if (temp != shownEntity) {
				hiddenEntities.remove(temp);
				if (shownEntity != null) {
					hiddenEntities.add(shownEntity);
				}
				shownEntity = temp;
			}
		}
	}
		
	public void setCameraInBounds() {
		// if camera goes out of map bounds bring it back in.
		boolean changePos = false;
		Vec3 position = new Vec3(getCameraPosX(), getCameraPosY(), getCameraPosZ());
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
			camera.setPosition(position);
		}
	}
	
	private float getCameraPosX() {
		return camera.getPosition().getX();
	}

	private float getCameraPosY() {
		return camera.getPosition().getY();
	}

	private float getCameraPosZ() {
		return camera.getPosition().getZ();
	}
}
