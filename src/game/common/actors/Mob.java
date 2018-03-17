
package game.common.actors;

import java.io.Serializable;
import java.util.HashMap;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import game.common.logic.ActorLogic;
import game.common.logic.MobLogic;

public class Mob extends NPC {
	
	private String name;
	private int mobId;
	private MobLogic logic;
	private float HEALTH_LIMIT;
	private float health;
	private float ENERGY_LIMIT;
	private float energy;
	private float resistance;
	private float attackRange;
	private Vec3 position;
	
	// temporary
	private Vec3 targetLocation;
	
	public Mob(int mobId, String name, Application app) {
		this.mobId = mobId;
		this.name = name;

		entity = new Entity("Mob"+ mobId);

		entity.addComponent(new NetIdentityComponent(mobId, app.getNetworkManager()));
		entity.addComponent(new NetTransformComponent());
		NetTransformComponent transform = (NetTransformComponent) entity.getComponent(NetTransformComponent.class);
		transform.setPosition(new Vec3(0.0f, 0.0f, -0.7f));

		logic = new MobLogic(this);

		NetDataComponent netData = new NetDataComponent();
		netData.addData("Health", health);
		netData.addData("Energy", energy);
		netData.addData("MovementSpeed", movementSpeed);
		netData.addData("Resistance", resistance);

		entity.addComponent(netData);

		// Colliders go here too

		movementSpeed = 0.02f; // to be set accordingly?

		if (!app.isHeadless()) {
			SpriteComponent spriteComponent = new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f),
			app.getAssetManager().getTexture("res/textures/characters/placeholder.png"), 1.5f, 1.5f);
			entity.addComponent(spriteComponent);
		}

		targetLocation = new Vec3();

	}
	/*
	public void lostHealth(float damage) {
		if (debug) { System.out.println("About to lose health"); }
		if(entity.hasComponent(NetDataComponent.class)) {
			NetDataComponent playerData = (NetDataComponent) entity.getComponent(NetDataComponent.class);
			HashMap<String, Serializable> health = playerData.getAllData("Health");

			if (debug) {
				System.out.println("LOSING HEALTH");
				System.out.println("Health:" + health.get("Health"));
				System.out.println("Damage Received: " + damage);
			}

			//Update Health.
			health.put("Health",Float.parseFloat(playerData.getData("Health").toString()) - damage);
			if( Float.parseFloat(playerData.getData("Health").toString()) <= 0 ){
				if (debug) { System.out.println("I Died..."); }
				logic.respawn(this);
			}
			System.out.println("New Health: " + health.get("Health"));
		}
	}
	*/
	public void setHealthLimit(float health) {
		HEALTH_LIMIT = health;
	}
	
	public float getHealthLimit() {
		return HEALTH_LIMIT;
	}
	

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	/**
	 * This will be affected by casting spells
	 */
	
	public float getEnergyLimit() {
		return ENERGY_LIMIT;
	}
	
	public void setEnergyLimit(float lim) {
		ENERGY_LIMIT = lim;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	/**
	 * This will be affected by items
	 */

	public float getResistance() {
		return resistance;
	}

	public void setResistance(float resistance) {
		this.resistance = resistance;
	}
	
	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float rng) {
		attackRange = rng;
	}
	
	
	public void update() {
		
	}
	

}
