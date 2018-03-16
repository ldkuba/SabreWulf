package game.common.actors;

import engine.AI.Navmesh;
import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import game.common.classes.AbstractClasses;

/**
 * Player as the engine sees it.
 */

public class Player extends Actor {

	private String name;
	private AbstractClasses role;
	private int playerId;

	public Player(int playerId, String name, Application app) 
	{	
		super(playerId, app);
		
		super.init("res/actors/archer/");
		
		this.playerId = playerId;
		this.name = name;
	}

	public AbstractClasses getRole() {
		return role;
	}

	/*
	public void setRole(AbstractClasses role) {
		//setPlayer(role);	//Add the players statistics.
		this.role = role;
	}
	*/

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
	public void update()
	{
		super.update();
	}
}