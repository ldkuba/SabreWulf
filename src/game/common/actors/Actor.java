package game.common.actors;

import java.util.ArrayList;
import java.util.LinkedList;

import engine.AI.Navmesh;
import engine.AI.PathThread;
import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.ColliderComponent;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteAnimationComponent;
import engine.entity.component.TransformComponent;
import engine.gui.components.ActorStatus;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import game.common.classes.AbstractClass;
import game.common.inventory.Inventory;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.Damage;
import game.common.items.attributes.main.Energy;
import game.common.items.attributes.main.Health;
import game.common.items.attributes.main.MovementSpeed;
import game.common.items.attributes.main.Resistance;
import game.common.logic.actions.Action;

/**
 * The actor class is extended by any player or character in the game
 * 
 * @author SabreWulf
 *
 */
public class Actor {

	protected Inventory inventory;
	protected Entity entity;
	protected NetDataComponent netData;
	protected int team;

	protected float HEALTH_LIMIT;
	protected float health;
	protected float healthRegen;

	protected float ENERGY_LIMIT;
	protected float energy;
	protected float energyRegen;

	protected float movementSpeed;
	protected float resistance;
	protected float damage;
	protected float attackRange;

	protected NetSpriteAnimationComponent netSprite;
	protected SpriteAnimationComponent sprite;
	protected Application app;
	protected ActorStatus status;
	protected AbstractClass role;
	private Action baseAttack = null;
	private PathThread pathThread;

	private Vec3 startPosition;
	private ArrayList<Vec3> currentPath;
	private ArrayList<Action> abilities;
	private boolean shouldHaveStatus = true;
	private boolean debug = true;
	private int id;
	// -1 = stop, 0 = up, 1 = left, 2 = down, 3 = right
	private int movingDir = -1;
	private final float MIN_DISTANCE = 0.2f;
	private int MOVE_ANIMATION_LENGTH;
	private int MOVE_ANIMATION_RIGHT;
	private int MOVE_ANIMATION_UP;
	private int MOVE_ANIMATION_LEFT;
	private int MOVE_ANIMATION_DOWN;

	/**
	 * Create a new actor with the given network id. This involves initialising
	 * all stats which are known as net data components
	 * 
	 * @param netId
	 * @param app
	 */
	public Actor(int netId, Application app) {
		this.app = app;
		entity = new Entity("Actor");
		currentPath = new ArrayList<>();
		this.id = netId;

		entity.addComponent(new NetIdentityComponent(netId, app.getNetworkManager()));
		entity.addComponent(new NetTransformComponent());
		NetTransformComponent transform = (NetTransformComponent) entity.getComponent(NetTransformComponent.class);
		transform.setPosition(new Vec3(0.0f, 0.0f, 0.0f));
		
		netData = new NetDataComponent();
		netData.addData("MaxHealth", HEALTH_LIMIT);
		netData.addData("Health", health);
		netData.addData("MaxEnergy", ENERGY_LIMIT);
		netData.addData("Energy", energy);
		netData.addData("MovementSpeed", movementSpeed);
		netData.addData("Resistance", resistance);
		netData.addData("Damage", damage);
		netData.addData("AttackRange", attackRange);
		netData.addData("Team", team);
		netData.addData("HealthRegen", healthRegen);
		netData.addData("EnergyRegen", energyRegen);
		entity.addComponent(netData);

		ColliderComponent collider = new ColliderComponent(1.5f, false);
		entity.addComponent(collider);
		netSprite = new NetSpriteAnimationComponent(0, 0, 2); // 2 is speed

		stopMovement();
		entity.addComponent(netSprite);
		entity.addTag("Targetable");
	}

	/**
	 * Initialise the actor if it is not a headless client
	 * 
	 * @param basePath
	 */
	public void init(String basePath) {
		if (!app.isHeadless()) {
			sprite = new SpriteAnimationComponent(app.getAssetManager().getTexture(basePath + "textures/sprite.png"), 4,
					0, 0, 5.0f, 5.0f, 2);
			entity.addComponent(sprite);
			if(shouldHaveStatus){
				status = new ActorStatus(this, app);
			}
		}
	}

	/**
	 * Add an item - affects the actors stats
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		addItemAttributes(item);
		inventory.addItem(item);
	}

	/**
	 * Add item attributes - affect the stats of the player.
	 * 
	 * @param item
	 */
	private void addItemAttributes(Item item) {
		LinkedList<Attribute> attributes = item.getAttributes();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			if (attribute instanceof Damage) {
				float newDamage = getDamage() + attribute.getValue();
				setDamage(newDamage);
			} else if (attribute instanceof Energy) {
				float newEnergy = getEnergyLimit() + attribute.getValue();
				setEnergy(newEnergy);
			} else if (attribute instanceof Health) {
				float newHealth = getHealthLimit() + attribute.getValue();
				setHealth(newHealth);
			} else if (attribute instanceof MovementSpeed) {
				float newMoveSpeed = getMovementSpeed() + attribute.getValue();
				setMovementSpeed(newMoveSpeed);
			} else if (attribute instanceof Resistance) {
				float newResis = getResistance() + attribute.getValue();
				setResistance(newResis);
			}
		}
		updateNetData();
	}

	/**
	 * Remove an item - affect actors stats
	 * 
	 * @param item
	 */
	public void removeItem(Item item) {
		remItemAttributes(item);
		inventory.rmvItem(item);
	}

	/**
	 * Remove an items attributes - affect the players stats
	 * 
	 * @param item
	 */
	private void remItemAttributes(Item item) {
		LinkedList<Attribute> attributes = item.getAttributes();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = attributes.get(i);
			if (attribute instanceof Damage) {
				float newDamage = getDamage() - attribute.getValue();
				setDamage(newDamage);
			} else if (attribute instanceof Energy) {
				float newEnergy = getEnergyLimit() - attribute.getValue();
				setEnergy(newEnergy);
			} else if (attribute instanceof Health) {
				float newHealth = getHealthLimit() - attribute.getValue();
				setHealth(newHealth);
			} else if (attribute instanceof MovementSpeed) {
				float newMoveSpeed = getMovementSpeed() - attribute.getValue();
				setMovementSpeed(newMoveSpeed);
			} else if (attribute instanceof Resistance) {
				float newResis = getResistance() - attribute.getValue();
				setResistance(newResis);
			}
		}
		updateNetData();
	}

	/**
	 * Add attributes picked up by items
	 * 
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute) {
		if (attribute instanceof Damage) {
			float newDamage = getDamage() + attribute.getValue();
			setDamage(newDamage);
		} else if (attribute instanceof Energy) {
			float newEnergy = getEnergyLimit() + attribute.getValue();
			setEnergy(newEnergy);
		} else if (attribute instanceof Health) {
			float newHealth = getHealthLimit() + attribute.getValue();
			setHealth(newHealth);
		} else if (attribute instanceof MovementSpeed) {
			float newMoveSpeed = getMovementSpeed() + attribute.getValue();
			setMovementSpeed(newMoveSpeed);
		} else if (attribute instanceof Resistance) {
			float newResis = getResistance() + attribute.getValue();
			setResistance(newResis);
		}
		updateNetData();
	}

	/**
	 * Remove attributes from items that have been used
	 * 
	 * @param attribute
	 */
	public void remAttribute(Attribute attribute) {
		if (attribute instanceof Damage) {
			float newDamage = getDamage() - attribute.getValue();
			setDamage(newDamage);
		} else if (attribute instanceof Energy) {
			float newEnergy = getEnergyLimit() - attribute.getValue();
			setEnergy(newEnergy);
		} else if (attribute instanceof Health) {
			float newHealth = getHealthLimit() - attribute.getValue();
			setHealth(newHealth);
		} else if (attribute instanceof MovementSpeed) {
			float newMoveSpeed = getMovementSpeed() - attribute.getValue();
			setMovementSpeed(newMoveSpeed);
		} else if (attribute instanceof Resistance) {
			float newResis = getResistance() - attribute.getValue();
			setResistance(newResis);
		}
		updateNetData();
	}

	/**
	 * Get the inventory
	 * 
	 * @return
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Clear the inventory
	 */
	public void cleanInventory() {
		inventory.clear();
	}

	/**
	 * Get an entity
	 * 
	 * @return
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Get the actors unique network id
	 * 
	 * @return
	 */
	public int getActorId() {
		return this.id;
	}

	/**
	 * Calculate the path through the navmesh for the actor to follow in order
	 * to get to a destination
	 * 
	 * @param target
	 * @param navmesh
	 */
	public void calculatePath(Vec3 target, Navmesh navmesh) {
		Vec3 startPos = new Vec3();

		if (entity.hasComponent(TransformComponent.class)) {
			startPos = entity.getTransform().getPosition();
		} else if (entity.hasComponent(NetTransformComponent.class)) {
			startPos = ((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).getPosition();
		}

		if (pathThread != null) {
			// kill it!
			pathThread.MAKEITSTOP();
		}

		pathThread = new PathThread(this, navmesh, startPos, target);
		pathThread.start();
	}

	/**
	 * Change the current path to a new path
	 * 
	 * @param path
	 */
	public void callbackPath(ArrayList<Vec3> path) {
		currentPath = path;
	}

	/**
	 * Clear path
	 */
	public void clearPath() {
		currentPath = new ArrayList<>();
	}

	/**
	 * Set actor start position
	 * 
	 * @param pos
	 */
	public void setStartPosition(Vec3 pos) {
		startPosition = pos;
	}

	/**
	 * Get actor start position
	 * 
	 * @return
	 */
	public Vec3 getStartPosition() {
		return startPosition;
	}

	/**
	 * Get the actors team team can be 1, 2, 3 team 1 is composed of three
	 * players (first three inTc lobby) team 2 is composed of the other three
	 * players (last three in lobby) team 3 is composed of neutral npcs (shops,
	 * cart etc)
	 */
	public int getTeam() {
		return team;
	}

	/**
	 * Set the actors team team can be 1, 2, 3 team 1 is composed of three
	 * players (first three inTc lobby) team 2 is composed of the other three
	 * players (last three in lobby) team 3 is composed of neutral npcs (shops,
	 * cart etc)
	 * 
	 * @param team
	 */
	public void setTeam(int team) {
		this.team = team;
	}

	/**
	 * Set health limit for the actor
	 * 
	 * @param health
	 */
	public void setHealthLimit(float health) {
		HEALTH_LIMIT = health;
	}

	/**
	 * Get the actors health limit
	 * 
	 * @return
	 */
	public float getHealthLimit() {
		return HEALTH_LIMIT;
	}

	/**
	 * Get the actors current health
	 * 
	 * @return
	 */
	public float getHealth() {
		return health;
	}

	/**
	 * Set the actors current health
	 * 
	 * @param health
	 */
	public void setHealth(float health) {
		this.health = health;
	}

	/**
	 * Get the actors energy limit
	 * 
	 * @return
	 */
	public float getEnergyLimit() {
		return ENERGY_LIMIT;
	}

	/**
	 * Set the actors energy limit
	 * 
	 * @param lim
	 */
	public void setEnergyLimit(float lim) {
		ENERGY_LIMIT = lim;
	}

	/**
	 * Get the actors current energy
	 * 
	 * @return
	 */
	public float getEnergy() {
		return energy;
	}

	/**
	 * Set the actors current energy
	 * 
	 * @param energy
	 */
	public void setEnergy(float energy) {
		this.energy = energy;
	}

	/**
	 * Set the actors movement speed
	 * 
	 * @param movementSpeed
	 */
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	/**
	 * Get the actors movement speed
	 * 
	 * @return
	 */
	public float getMovementSpeed() {
		return movementSpeed;
	}

	/**
	 * Get the actors resistance
	 * 
	 * @return
	 */
	public float getResistance() {
		return resistance;
	}

	/**
	 * Set the actors resistance
	 * 
	 * @param resistance
	 */
	public void setResistance(float resistance) {
		this.resistance = resistance;
	}

	/**
	 * Get the damage of the actor
	 * 
	 * @return
	 */
	public float getDamage() {
		return damage;
	}

	/**
	 * Set the actor damage
	 * 
	 * @param dmg
	 *
	 */
	public void setDamage(float dmg) {
		damage = dmg;
	}

	/**
	 * Get the attackers attack range
	 * 
	 * @return
	 */
	public float getAttackRange() {
		return attackRange;
	}

	/**
	 * Set the attackers attack range
	 * 
	 * @param rng
	 */
	public void setAttackRange(float rng) {
		attackRange = rng;
	}

	/**
	 * Get the health regeneration of the actor
	 * 
	 * @return
	 */
	public float getHealthRegen() {
		return healthRegen;
	}

	/**
	 * Set the health regeneration of the actor
	 * 
	 * @param healthRegen
	 */
	public void setHealthRegen(float healthRegen) {
		this.healthRegen = healthRegen;
	}

	/**
	 * Get the energy regeneration of the actor
	 * 
	 * @return
	 */
	public float getEnergyRegen() {
		return energyRegen;
	}

	/**
	 * Set the energy regeneration of the actor
	 * 
	 * @param energyRegen
	 */
	public void setEnergyRegen(float energyRegen) {
		this.energyRegen = energyRegen;
	}

	/**
	 * Update the network data for the given player
	 */
	private void updateNetData() {
		netData = (NetDataComponent) entity.getComponent(NetDataComponent.class);

		if (netData == null) {
			return;
		}

		if (app.isHeadless()) {
			netData.addData("MaxHealth", HEALTH_LIMIT);
			netData.addData("Health", health);
			netData.addData("MaxEnergy", ENERGY_LIMIT);
			netData.addData("Energy", energy);
			netData.addData("MovementSpeed", movementSpeed);
			netData.addData("Resistance", resistance);
			netData.addData("Damage", damage);
			netData.addData("AttackRange", attackRange);
			netData.addData("Team", team);
			netData.addData("HealthRegen", healthRegen);
			netData.addData("EnergyRegen", energyRegen);
		} else {
			HEALTH_LIMIT = (float) netData.getData("MaxHealth");
			health = (float) netData.getData("Health");
			ENERGY_LIMIT = (float) netData.getData("MaxEnergy");
			energy = (float) netData.getData("Energy");
			movementSpeed = (float) netData.getData("MovementSpeed");
			resistance = (float) netData.getData("Resistance");
			damage = (float) netData.getData("Damage");
			attackRange = (float) netData.getData("AttackRange");
			team = (int) netData.getData("Team");
			healthRegen = (float) netData.getData("HealthRegen");
			energyRegen = (float) netData.getData("EnergyRegen");
		}
	}

	/**
	 * Update the actor through updating its net data, status, stats and
	 * position
	 */
	public void update() {
		updateNetData();

		if(baseAttack != null) {
			baseAttack.changeCooldown(app.getTimer().getElapsedTime() / 1000.0f);
		}

		//update cooldown for abilities
        for(int i = 0; i < abilities.size(); i++) {
            abilities.get(i).changeCooldown(app.getTimer().getElapsedTime() / 1000.0f);
        }
		
		if(!app.isHeadless() && shouldHaveStatus)
		{
			status.update();			
		} else {
			Vec3 currentPos = new Vec3();
			if (entity.hasComponent(TransformComponent.class)) {
				if (!currentPath.isEmpty()) {
					currentPos = new Vec3(entity.getTransform().getPosition());
					currentPos.scale(-1.0f);
					Vec3 moveDir = Vec3.add(currentPath.get(0), currentPos);
					if (moveDir.getLength() < MIN_DISTANCE) {
						entity.getTransform().setPosition(currentPath.get(0));
						currentPath.remove(0);
						if (currentPath.isEmpty()) {
							stopMovement();
						}
					} else {
						moveDir = Vec3.normalize(moveDir);
						moveDir.scale(movementSpeed);
						entity.getTransform().move(moveDir);
					}
				}
			} else if (entity.hasComponent(NetTransformComponent.class)) {
				/* Respawn */
				if (health <= 0) {
					startPosition = new Vec3(10.0f, -10.0f, 0.0f);
					entity.getNetTransform().setPosition(startPosition);
					health = HEALTH_LIMIT;
				}
				if (!currentPath.isEmpty()) {
					currentPos = new Vec3(
							((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).getPosition());
					currentPos.scale(-1.0f);

					Vec3 moveDir = Vec3.add(currentPath.get(0), currentPos);
					if (moveDir.getLength() < MIN_DISTANCE) {
						((NetTransformComponent) entity.getComponent(NetTransformComponent.class))
								.setPosition(currentPath.get(0));
						currentPath.remove(0);
						if (currentPath.isEmpty()) {
							stopMovement();
						}
					} else {
						moveDir = Vec3.normalize(moveDir);
						moveDir.scale(movementSpeed);
						int direction = MathUtil.dirTo4Dir(moveDir);
						if (direction == 0) {
							moveUp();
						} else if (direction == 1) {
							moveLeft();
						} else if (direction == 2) {
							moveDown();
						} else if (direction == 3) {
							moveRight();
						}
						((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).move(moveDir);
					}
				}
			}
			currentPos.scale(-1.0f);
		}
	}

	/**
	 * Stop the actor from moving
	 */
	public void stopMovement() {
		if (movingDir != -1) {
			netSprite.stopAnimation();
			movingDir = -1;
		}
	}

	/**
	 * Move the actor up
	 */
	public void moveUp() {
		if (movingDir != 0) {
			netSprite.changeAnimationFrames(MOVE_ANIMATION_UP, MOVE_ANIMATION_UP + MOVE_ANIMATION_LENGTH);
			movingDir = 0;
		}
	}

	/**
	 * Move the actor left
	 */
	public void moveLeft() {
		if (movingDir != 1) {
			netSprite.changeAnimationFrames(MOVE_ANIMATION_LEFT, MOVE_ANIMATION_LEFT + MOVE_ANIMATION_LENGTH);
			movingDir = 1;
		}
	}

	/**
	 * Move the actor down
	 */
	public void moveDown() {
		if (movingDir != 2) {
			netSprite.changeAnimationFrames(MOVE_ANIMATION_DOWN, MOVE_ANIMATION_DOWN + MOVE_ANIMATION_LENGTH);
			movingDir = 2;
		}
	}

	/**
	 * Move the actor right
	 */
	public void moveRight() {
		if (movingDir != 3) {
			netSprite.changeAnimationFrames(MOVE_ANIMATION_RIGHT, MOVE_ANIMATION_RIGHT + MOVE_ANIMATION_LENGTH);
			movingDir = 3;
		}
	}

	/**
	 * Set the role of the actor and assign it entity role stats
	 * 
	 * @param role
	 */
	public void setRole(AbstractClass role) {
		this.role = role;

		if (!app.isHeadless()) {
			this.init(role.getResourcePath());
		}

		setStatistics();
		// update player statistics.
		updateNetData();
		if (debug) {
			System.err.println("Entity role stats assigned");
		} else {
			if (debug) {
				System.err.println("WARNING: Entity does not have role stats assgined");
			}
		}
	}

	/**
	 * Get the actors role
	 * 
	 * @return
	 */
	public AbstractClass getRole() {
		return role;
	}

	/**
	 * Set name to the role
	 *
	 * @param name
	 */
	public void setRoleName(String name) {
		role.setName(name);
	}

	/**
	 * Get role name
	 * @return
	 */
	public String getRoleName() {
		return role.getName();
	}

	/**
	 * Get the actors base attack
	 * 
	 * @return
	 */
	public Action getBaseAttack() {
		return baseAttack;
	}

	/**
	 * Get the actors abilities
	 * 
	 * @return
	 */
	public ArrayList<Action> getAbilities() {
		return abilities;
	}

	/**
	 * Set the statistics for the actor - dependant on character chouse
	 */
	public void setStatistics() {
		HEALTH_LIMIT = role.getHealth();
		resistance = role.getResistance();
		movementSpeed = role.getMoveSpeed();
		ENERGY_LIMIT = role.getEnergy();
		health = HEALTH_LIMIT;
		energy = ENERGY_LIMIT;
		damage = role.getDamage();
		attackRange = role.getAttackRange();

		MOVE_ANIMATION_LENGTH = role.getMoveAnimationLength();
		MOVE_ANIMATION_LEFT = role.getMoveAnimationLeft();
		MOVE_ANIMATION_RIGHT = role.getMoveAnimationRight();
		MOVE_ANIMATION_UP = role.getMoveAnimationUp();
		MOVE_ANIMATION_DOWN = role.getMoveAnimationDown();

		healthRegen = role.getHealthReg();
		energyRegen = role.getEnergyReg();

		baseAttack = role.getBaseAttack();
		abilities = role.getAbilities();
	}

	/**
	 * Get the actors status bars
	 * 
	 * @return
	 */
	public ActorStatus getStatus() {
		return status;
	}

	public ArrayList<Vec3> getCurrentPath() {
		return currentPath;
	}
	
	public void shouldHaveStatus(boolean decision){
		this.shouldHaveStatus = decision;
	}
	
	public boolean hasStatus(){
		return shouldHaveStatus;
	}
}
