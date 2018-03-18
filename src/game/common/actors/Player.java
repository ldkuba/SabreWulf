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
 * Player as the engine sees it.
 */

public class Player extends Actor {

	private String name;

	public Player(int playerId, String name, Application app) {
		super(playerId, app);

		// decide which one
		super.init("res/actors/link/");
		this.name = name;
	}

	public AbstractClass getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update() {
		super.update();
	}
}