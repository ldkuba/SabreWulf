package game.map;

import java.util.ArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.graphics.camera.Camera;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map
{
	private ArrayList<Entity> entities;
	private Entity[] background;
	private Camera camera;
	
	private final int MAP_SIZE = 10;
	private Scene scene;

	public Map(Scene scene)
	{
		this.scene = scene;
		camera = scene.getCamera();
		entities = new ArrayList<Entity>();
		
		background = new Entity[MAP_SIZE * MAP_SIZE];
	}

	public void init()
	{
		
	}
	
	public ArrayList<Entity> getMapEntities()
	{
		return entities;
	}

	public void update()
	{ 
		
	}
}
