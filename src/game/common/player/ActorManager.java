package game.common.player;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;
import engine.scene.Scene;
import game.client.Main;
import game.common.actors.Actor;
import game.common.actors.NPC;
import game.common.actors.Player;
import game.common.map.Map;

/**
 * Manages all actors in the game.
 *
 * @author Sabrewulf
 */

public class ActorManager
{
	private ArrayList<Actor> actors;
	private Scene scene;
	private int nextID;
	private Map map;
	
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
		nextID++;
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
			if(actor instanceof Player) {
				actor.update();
			} else if(actor instanceof NPC) {
				//Client would not have NPCs netTransform in the beginning
				if(actor.getEntity().getNetTransform() == null) {
					actor.update();
				} else {
					Vec3 position = actor.getEntity().getNetTransform().getPosition();

					//Check if there is a desired location.
					if (((NPC) actor).getAllLocations().size() > 0 ) {
						Vec3 target = ((NPC) actor).seeTargetLocation();

						//Have I reached my desired location?
						if (position.getX() == target.getX() && position.getY() == target.getY()) {
							((NPC) actor).removeDesiredLocation();
							actor.stopMovement();
							System.out.println("Reached target");
						}

						//Acquire Path
						if (actor.getCurrentPath().size() == 0 && ((NPC) actor).getAllLocations().size() > 0) {
							actor.calculatePath(((NPC) actor).seeTargetLocation(), map.getNavmesh());
							actor.getCurrentPath().add(new Vec3(0,0,0));
						}
					}
					actor.update();
				}
			}
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

	public int getNextID() {
		return nextID;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
