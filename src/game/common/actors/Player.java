package game.common.actors;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;

/**
 * Player as the engine sees it.
 */

public class Player extends Actor
{
	private String name;
	private int role;
	private int playerId;
	
	//temporary
	private Vec3 targetLocation;

	public Player(int playerId, String name, Application app)
	{	
		this.playerId = playerId;
		this.name = name;
	
		entity = new Entity("Player" + playerId);
		
		entity.addComponent(new NetIdentityComponent(playerId, app.getNetworkManager()));
		entity.addComponent(new NetTransformComponent());
		
		NetDataComponent netData = new NetDataComponent();
		netData.addData("Health", health);
		netData.addData("Energy", energy);
		netData.addData("MovementSpeed", movementSpeed);
		netData.addData("Resistance", resistance);		
		netData.addData("Team", team);		
		
		entity.addComponent(netData);
		
		//Colliders go here too
		
		if(!app.isHeadless())
		{
			SpriteComponent spriteComponent = new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), app.getAssetManager().getTexture("res/textures/characters/placeholder.png"), 1.5f, 1.5f);
			entity.addComponent(spriteComponent);
		}
		
		targetLocation = new Vec3();
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getPlayerId()
	{
		return this.playerId;
	}
	
	public void update()
	{
		NetTransformComponent transform = (NetTransformComponent) entity.getComponent(NetTransformComponent.class);

		Vec3 currentPos = new Vec3(transform.getPosition());
		
		currentPos.scale(-1.0f);
		
		Vec3 moveDir = Vec3.add(targetLocation, currentPos);
		moveDir = Vec3.normalize(moveDir);
		moveDir.scale(movementSpeed);
		
		transform.move(moveDir);
	}
	
	public void setTargetLocation(Vec3 target)
	{
		this.targetLocation = target;
	}
}