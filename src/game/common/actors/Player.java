package game.common.actors;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import game.common.classes.AbstractClass;

/**
 * Player as the engine sees it
 * @author SabreWulf
 *
 */
public class Player extends Actor {

	private String name;
	private boolean isLocal;
	
	/**
	 * Creates a new player
	 * @param playerId
	 * @param name
	 * @param app
	 * @param isLocal
	 */
	public Player(int playerId, String name, Application app, boolean isLocal) {
		super(playerId, app);
		this.name = name;
		this.isLocal = isLocal;
	}
	
	/**
	 * Gets the name of the player
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if the player is connected locally
	 * @return
	 */
	public boolean isLocal()
	{
		return this.isLocal;
	}
	
	/**
	 * Updates the player 
	 */
	@Override
	public void update() {
		super.update();
	}
}