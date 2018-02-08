package game.map;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.entity.component.*;
import engine.graphics.camera.Camera;

public class Map {
	ArrayList<Entity> entities;
	Entity[] background;
	TransformComponent transformComponent;
	SpriteComponent spriteComponent;
	Camera camera;
	float currentX;
	float currentY;
	float currentZ;
	private final int MAP_SIZE = 10; 
	
	//Navmesh
	public Map(){
		entities = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE];
	}
	
	public void addEntity(Entity entity){
		if (!entities.contains(entity)){
			entities.add(entity);
		}
	}
	
	private float getCameraPosX(){
		return camera.getPosition().getX();
	}
	
	private float getCameraPosY(){
		return camera.getPosition().getY();
	}
	
	private float getCameraPosZ(){
		return camera.getPosition().getZ();
	}
	
	public void init(){
		
	}
	
}
