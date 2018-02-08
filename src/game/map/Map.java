package game.map;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.scene.Scene;

public class Map {
	ArrayList<Entity> Entities;
	Entity[] background;
	//Navmesh
	
	Scene scene;
	
	public Map(Scene scene)
	{
		this.scene = scene;
	}
	
	
}
