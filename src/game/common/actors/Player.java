package game.common.actors;

import engine.AI.Navmesh;
import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import game.common.classes.AbstractClasses;
import game.common.logic.ActorLogic;

/**
 * Player as the engine sees it.
 */

public class Player extends Actor {

	private String name;
	private AbstractClasses role;
	private int playerId;
	private Vec3 startingPos;

	private int team;

	// temporary
	private Vec3 targetLocation;

	public Player(int playerId, String name, Application app) {
		this.playerId = playerId;
		this.name = name;

		entity = new Entity("Player" + playerId);

		entity.addComponent(new NetIdentityComponent(playerId, app.getNetworkManager()));
		entity.addComponent(new NetTransformComponent());
		NetTransformComponent transform = (NetTransformComponent) entity.getComponent(NetTransformComponent.class);
		transform.setPosition(new Vec3(0.0f, 0.0f, -0.7f));

		logic = new ActorLogic(this);

		NetDataComponent netData = new NetDataComponent();
		netData.addData("Health", health);
		netData.addData("Energy", energy);
		netData.addData("MovementSpeed", movementSpeed);
		netData.addData("Resistance", resistance);
		netData.addData("Team", team);

		entity.addComponent(netData);

		// Colliders go here too

		movementSpeed = 0.02f;

		if (!app.isHeadless()) {
			SpriteComponent spriteComponent = new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f),
			app.getAssetManager().getTexture("res/textures/characters/placeholder.png"), 1.5f, 1.5f);
			entity.addComponent(spriteComponent);
		}

		targetLocation = new Vec3();

	}

	public AbstractClasses getRole() {
		return role;
	}

	public void setRole(AbstractClasses role) {
		//setPlayer(role);	-> Breaks the movement

		NetDataComponent data = (NetDataComponent) entity.getComponent(NetDataComponent.class);
		this.role = role;
		HEALTH_LIMIT = role.getHealth();
		ENERGY_LIMIT = role.getEnergy();
		data.getAllData("Health").put("Health", Float.parseFloat(data.getData("Health").toString()) + role.getHealth());
		data.getAllData("Energy").put("Energy", Float.parseFloat(data.getData("Energy").toString()) + role.getEnergy());
		data.getAllData("MovementSpeed").put("MovementSpeed", Float.parseFloat(data.getData("MovementSpeed").toString()) + role.getMoveSpeed());
		data.getAllData("Resistance").put("Resistance", Float.parseFloat(data.getData("Resistance").toString()) + role.getResistance());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTeam(int team) {
		this.team = team;
		switch(team) {
			case 1:
				//startingPos = team 1 start pos
				break;
			case 2:
				// startingPos = team 2 start pos
				break;
		}
	}

	public int getTeam() {
		return team;
	}

	public Vec3 getStartingPos() {
		return startingPos;
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