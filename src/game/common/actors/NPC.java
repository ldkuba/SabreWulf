package game.common.actors;

import engine.application.Application;
import engine.maths.Vec3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Non-playable characters (NPCs) as the engine sees it
 * @author SabreWulf
 *
 */
public class NPC extends Actor{

	private Queue<Vec3> targetLocation;
	private String name;

	/**
	 * Creates a new NPC
	 *
	 * @param name
	 * @param app
	 * @param networkId
	 */
	public NPC(String name,int networkId, Application app) {
		super(networkId, app);
		targetLocation = new LinkedList<>();
		this.name = name;
	}

	/**
	 * Get name of NPC
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Updates the NPC
	 */
	public void update() {
		super.update();
	}

	public Vec3 seeTargetLocation() {
		return targetLocation.peek();
	}

	/**
	 * Removes the head of the target list
	 */
	public void removeDesiredLocation() {
		targetLocation.poll();
	}

	/**
	 * Adds the next destination for the NPC
	 * @param target
	 */
	public void addDesiredLocation(Vec3 target) {
		targetLocation.add(target);
	}

	public Queue<Vec3> getAllLocations() {
		return targetLocation;
	}
	 
}
