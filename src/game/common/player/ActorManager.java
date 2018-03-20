package game.common.player;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.scene.Scene;
import game.common.actors.Actor;
import game.common.actors.Player;

public class ActorManager
{
	private ArrayList<Actor> actors;
	private Scene scene;
	
	public ActorManager(Scene scene)
	{
		actors = new ArrayList<Actor>();
		this.scene = scene;
	}
	
	public void addActor(Actor actor)
	{
		actors.add(actor);
		scene.addEntity(actor.getEntity());
	}
	
	public Actor getActor(int id)
	{
		for(Actor actor : actors)
		{
			if(id == actor.getActorId())
			{
				return actor;
			}
		}
		
		return null;
	}

	public Actor getActor(Entity e)
	{
		for(Actor actor : actors)
		{
			if(actor.getEntity().equals(e))
			{
				return actor;
			}
		}
		
		return null;
	}
	
	public ArrayList<Actor> getActors() {
		return actors;
	}
	
	public void update()
	{
		for(Actor actor : actors)
		{
			actor.update();
		}
	}
	
	public Actor getLocalPlayer()
	{
		for(Actor actor : actors)
		{
			if(actor instanceof Player)
			{
				Player player = (Player) actor;
				
				if(player.isLocal())
				{
					return actor;
				}
			}
		}
		
		return null;
	}
}
