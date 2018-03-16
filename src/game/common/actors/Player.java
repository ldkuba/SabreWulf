package game.common.actors;

import engine.application.Application;
import game.common.classes.AbstractClasses;

/**
 * Player as the engine sees it.
 */

public class Player extends Actor {

	private String name;
	private AbstractClasses role;
	private int playerId;

	public Player(int playerId, String name, Application app) {
		super(playerId, app);

		// decide which one
		super.init("res/actors/link/");

		this.playerId = playerId;
		this.name = name;
	}

	public AbstractClasses getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	@Override
	public void update() {
		super.update();
	}
}