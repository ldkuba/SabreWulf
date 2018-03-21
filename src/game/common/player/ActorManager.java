package game.common.player;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.scene.Scene;
import game.common.actors.Actor;
import game.common.actors.Player;

/**
 * Manages all actors in the game.
 *
 * @author Sabrewulf
 */

public class ActorManager
{
	private ArrayList<Actor> actors;
	private Scene scene;
	
	public ActorManager(Scene scene)
	{
		actors = new ArrayList<Actor>();
		this.scene = scene;
	}

	/**
	 * Add actor into the manager.
	 * Add the actor's entity into the scene.
	 *
	 * @param actor
	 */
	public void addActor(Actor actor)
	{
		actors.add(actor);
		scene.addEntity(actor.getEntity());
	}

	/**
	 * Get actor by its ID.
	 *
	 * @param id
	 * @return
	 */
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

	/**
	 * Get actor by its entity
	 *
	 * @param e
	 * @return
	 */
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

	/**
	 * Get all actors in game
	 *
	 * @return
	 */
	public ArrayList<Actor> getActors() {
		return actors;
	}

	/**
	 * Update all actors
	 */
	public void update()
	{
		for(Actor actor : actors)
		{
			actor.update();
		}
	}

	/**
	 * Get local actor
	 *
	 * @return
	 */
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
