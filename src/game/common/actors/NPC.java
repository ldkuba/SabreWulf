package game.common.actors;

import engine.application.Application;
import engine.maths.Vec3;
import game.client.Main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Non-playable characters (NPCs) as the engine sees it
 * @author SabreWulf
 *
 */
public class NPC extends Actor{

	private Queue<Vec3> destinationBuffer;
	private Vec3 targetLocation;
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
		destinationBuffer = new LinkedList<>();
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

	/**
	 * Removes the head of the target list
	 */
	public Vec3 removeDesiredLocation() {
		return destinationBuffer.poll();
	}

	/**
	 * Adds the next destination for the NPC
	 * @param target
	 */
	public void addDesiredLocation(Vec3 target) {
		destinationBuffer.add(target);
	}

	public Queue<Vec3> getDestinationBuffer() {
		return destinationBuffer;
	}

	/**
	 * Set target location for the NPC
	 * @param targetLocation
	 */
	public void setTargetLocation(Vec3 targetLocation) {
		this.targetLocation = targetLocation;
	}

	/**
	 * Get target location for the NPC
	 * @return
	 */
	public Vec3 getTargetLocation() {
		return targetLocation;
	}
}
