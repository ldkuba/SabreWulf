package game.map;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.graphics.camera.Camera;

public class Map {
	ArrayList<Entity> entities;
	Entity[] background;
	Camera camera;
	
	private final int MAP_SIZE = 10; 
	
	public Map(){
		entities = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE]; //map is 10 x 10
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
	
	public boolean inScope(){
		return true;
	}
	
	public void init(){
		for (int i = 0; i < MAP_SIZE; i++){
			background[i] = null; //do something here
		}
		
	}
}
