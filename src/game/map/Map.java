package game.map;

import java.util.ArrayList;

import engine.entity.Entity;<<<<<<<HEAD
import engine.scene.Scene;=======
import engine.graphics.camera.Camera;
import engine.maths.Vec3;>>>>>>>refs/remotes/origin/engine

public class Map
{
	ArrayList<Entity> entities;
	Entity[] background;

	Camera camera;
	Entity shownEntity;
	ArrayList<Entity> hiddenEntities;
	private final int MAP_SIZE = 10;

	public Map()
	{
		entities = new ArrayList<Entity>();
		hiddenEntities = new ArrayList<Entity>();
		background = new Entity[MAP_SIZE * MAP_SIZE]; // map is 10 x 10
	}

	public void addEntity(Entity entity)
	{
		if(!entities.contains(entity))
		{
			entities.add(entity);
		}
	}

	public void setShownEntity()
	{
		// returns correct part of background[] depending on camera position
		Entity temp;
		Vec3 cameraPos = new Vec3(getCameraPosX(), getCameraPosY(), getCameraPosZ());
		int i = (int) (cameraPos.getX() + ((float) MAP_SIZE * cameraPos.getY()));
		temp = background[i];
		if(entities.contains(temp))
		{
			if(temp != shownEntity)
			{
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
		Vec3 position = new Vec3(getCameraPosX(), getCameraPosY(), getCameraPosZ());
		if(position.getX() > (float) MAP_SIZE)
		{
			position.setX((float) MAP_SIZE);
			changePos = true;
		}else if(position.getX() < 0)
		{
			position.setX(0);
			changePos = true;
		}
		if(position.getY() > (float) MAP_SIZE)
		{
			position.setY((float) MAP_SIZE);
			changePos = true;
		}else if(position.getY() < 0)
		{
			position.setY(0);
			changePos = true;
		}
		if(position.getZ() > (float) MAP_SIZE)
		{
			position.setZ((float) MAP_SIZE);
			changePos = true;
		}else if(position.getZ() < 0)
		{
			position.setZ(0);
			changePos = true;
		}
		if(changePos)
		{
			camera.setPosition(position);
		}
	}

	public boolean inInScope()
	{
		// to do
		return true;
	}

	public void init()
	{
		// initialise entities
		for (Entity entity : entities)
		{
			hiddenEntities.add(entity);
		}
		setShownEntity();
	}

	public Entity getShownEntity()
	{
		return shownEntity;
	}

	public void renderer()
	{

	}

	private float getCameraPosX()
	{
		return camera.getPosition().getX();
	}

	private float getCameraPosY()
	{
		return camera.getPosition().getY();
	}

	private float getCameraPosZ() {
		return camera.getPosition().getZ();
	}
}
