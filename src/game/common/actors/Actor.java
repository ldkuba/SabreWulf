package game.common.actors;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import engine.AI.Navmesh;
import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import game.common.classes.AbstractClasses;
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
import game.common.classes.AbstractClasses;
import game.common.inventory.Inventory;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.Damage;
import game.common.items.attributes.main.Energy;
import game.common.items.attributes.main.Health;
import game.common.items.attributes.main.MovementSpeed;
import game.common.items.attributes.main.Resistance;
import game.common.logic.ActorLogic;
import game.common.logic.actions.Respawn;

public class Actor
{
	private boolean debug = true;

	private final int MOVE_ANIMATION_LENGTH = 7;
	private final int MOVE_ANIMATION_RIGHT = 0;
	private final int MOVE_ANIMATION_UP = 8;
	private final int MOVE_ANIMATION_LEFT = 16;
	private final int MOVE_ANIMATION_DOWN = 24;
	
	//-1 = stop, 0 = up, 1 = left, 2 = down, 3 = right
	private int movingDir = -1;
	
	private final float MIN_DISTANCE = 0.2f;
	private ArrayList<Vec3> currentPath;
	
	protected NetSpriteAnimationComponent netSprite;
	protected SpriteAnimationComponent sprite;	
	protected Application app;
	protected Inventory inventory;
	private AbstractClasses role;
	protected Entity entity;
	protected ActorLogic logic;
	protected ActorStatus status;
	
	protected int team;
	protected Vec3 base;
	protected Vec3 position;
	protected float energy;
	protected float movementSpeed;
	protected float attackRange = 2.0f;
	protected float HEALTH_LIMIT;
	protected float ENERGY_LIMIT;
	
	public Actor(int netId, Application app)
	{
		this.app = app;
		entity = new Entity("Actor");
		currentPath = new ArrayList<>();	
		
		entity.addComponent(new NetIdentityComponent(netId, app.getNetworkManager()));
		entity.addComponent(new NetTransformComponent());
		NetTransformComponent transform = (NetTransformComponent) entity.getComponent(NetTransformComponent.class);
		transform.setPosition(new Vec3(0.0f, 0.0f, 0.0f));
		
		NetDataComponent netData = new NetDataComponent();
		netData.addData("Health", health);
		netData.addData("Energy", energy);
		netData.addData("MovementSpeed", movementSpeed);
		netData.addData("Resistance", resistance);
		netData.addData("Team", team);
		entity.addComponent(netData);
		
		ColliderComponent collider = new ColliderComponent(1.5f, false);
		entity.addComponent(collider);
		
		netSprite = new NetSpriteAnimationComponent(0, 0, 2);
		
		stopMovement();
		entity.addComponent(netSprite);
		
		entity.addTag("Targetable");
		
		movementSpeed = 0.05f; //base for each actor
	}
	
	public void init(String basePath)
	{		
		if (!app.isHeadless()) {
			sprite = new SpriteAnimationComponent(app.getAssetManager().getTexture(basePath + "textures/sprite.png"), 4, 0, 0, 5.0f, 5.0f, 2);
			entity.addComponent(sprite);
			status = new ActorStatus(this, app);
		}
	}

	public void addItem(Item item) {
		//Affect the actors stats.
		addItemAttributes(item);
		inventory.addItem(item);
	}
	
	//Affect the stats of the player.
	private void addItemAttributes(Item item) {
		LinkedList<Attribute> attributes = item.getAttributes();
		
		for(int i = 0; i < attributes.size(); i++) {
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
		
	}

	public void removeItem(Item item) {
		//Affect the actors stats.
		remItemAttributes(item);
		inventory.rmvItem(item);
	}
	
	//Affect the players stats once the item has been removed.
	private void remItemAttributes(Item item) {
		LinkedList<Attribute> attributes = item.getAttributes();
		
		for(int i = 0; i < attributes.size(); i++) {
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
		
	}
	
	public void addAttribute(Attribute attribute)  {
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
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void cleanInventory() {
		inventory.clear();
	}

	public Entity getEntity() {
		return entity;
	}
	
	public void calculatePath(Vec3 target, Navmesh navmesh)
	{
		Vec3 startPos = new Vec3();
		
		if(entity.hasComponent(TransformComponent.class))
		{
			startPos = entity.getTransform().getPosition();
		}else if(entity.hasComponent(NetTransformComponent.class))
		{
			startPos = ((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).getPosition();
		}
		
		ArrayList<Vec3> path = navmesh.findPath(startPos, target);

		if(path != null)
		{
			path.add(target);
			this.currentPath = path;		
		}
	}

	public void lostHealth(float damage) {
		if (debug ) { System.out.println("About to lose health"); }
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


	/**
	 * team can be 1, 2, 3 team 1 is composed of three players (first three inTc
	 * lobby) team 2 is composed of the other three players (last three in
	 * lobby) team 3 is composed of neutral npcs (shops, cart etc)
	 */

	public int getTeam() {
		return team;
	}

	public Vec3 getBase() {
		return base;
	}

	public void setBase(Vec3 base) {
		this.base = base;
	}


	public Vec3 getPosition() {
		return position;
	}

	public void setPosition(Vec3 position) {
		this.position = position;
	}

	/**
	 * This will be affected by damage
	 */
	
	public void setHealthLimit(float health) {
		HEALTH_LIMIT = health;
	}
	
	public float getHealthLimit() {
		return HEALTH_LIMIT;
	}
	
	protected float health;

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
	 * This might be affected by items and root & snare spells
	 */
	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	/**
	 * This will be affected by items
	 */
	protected float resistance;

	public float getResistance() {
		return resistance;
	}

	public void setResistance(float resistance) {
		this.resistance = resistance;
	}

	public void update()
	{
		Vec3 currentPos = new Vec3();
		
		if(entity.hasComponent(TransformComponent.class))
		{
			if(!currentPath.isEmpty())
			{
				currentPos = new Vec3(entity.getTransform().getPosition());
				
				currentPos.scale(-1.0f);
				
				Vec3 moveDir = Vec3.add(currentPath.get(0), currentPos);
				
				if(moveDir.getLength() < MIN_DISTANCE)
				{
					entity.getTransform().setPosition(currentPath.get(0));
					
					currentPath.remove(0);	
					
					if(currentPath.isEmpty())
					{
						stopMovement();
					}	
				}else
				{
					moveDir = Vec3.normalize(moveDir);
					moveDir.scale(movementSpeed);
				
					entity.getTransform().move(moveDir);
				}
			}
		}else if(entity.hasComponent(NetTransformComponent.class))
		{
			if(!currentPath.isEmpty())
			{
				currentPos = new Vec3(((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).getPosition());
				
				currentPos.scale(-1.0f);
				
				Vec3 moveDir = Vec3.add(currentPath.get(0), currentPos);
				
				if(moveDir.getLength() < MIN_DISTANCE)
				{
					((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).setPosition(currentPath.get(0));
					
					currentPath.remove(0);
					
					if(currentPath.isEmpty())
					{
						stopMovement();
					}
				}else
				{
					moveDir = Vec3.normalize(moveDir);
					moveDir.scale(movementSpeed);
					
					int direction = MathUtil.dirTo4Dir(moveDir);
					
					if(direction == 0)
					{
						moveUp();
					}else if(direction == 1)
					{
						moveLeft();
					}else if(direction == 2)
					{
						moveDown();
					}else if(direction == 3)
					{
						moveRight();
					}
					
					((NetTransformComponent) entity.getComponent(NetTransformComponent.class)).move(moveDir);
				}
			}
		}
		
		currentPos.scale(-1.0f);

		System.out.println("Current Pos: " + currentPos.getX() + ", " + currentPos.getY());
		
		// Respawns player if health is <= 0
		/*if (!logic.isAlive(this)) {
			logic.respawn(this);
		}*/
	}
	
	public void stopMovement()
	{
		if(movingDir != -1)
		{
			netSprite.stopAnimation();
			movingDir = -1;
		}
	}
	
	public void moveUp()
	{
		if(movingDir != 0)
		{
			netSprite.changeAnimationFrames(MOVE_ANIMATION_UP, MOVE_ANIMATION_UP + MOVE_ANIMATION_LENGTH);
			movingDir = 0;
		}
	}
	
	public void moveLeft()
	{
		if(movingDir != 1)
		{
			netSprite.changeAnimationFrames(MOVE_ANIMATION_LEFT, MOVE_ANIMATION_LEFT + MOVE_ANIMATION_LENGTH);
			movingDir = 1;
		}
	}
	
	public void moveDown()
	{
		if(movingDir != 2)
		{
			netSprite.changeAnimationFrames(MOVE_ANIMATION_DOWN, MOVE_ANIMATION_DOWN + MOVE_ANIMATION_LENGTH);
			movingDir = 2;
		}
	}
	
	public void moveRight()
	{
		if(movingDir != 3)
		{
			netSprite.changeAnimationFrames(MOVE_ANIMATION_RIGHT, MOVE_ANIMATION_RIGHT + MOVE_ANIMATION_LENGTH);
			movingDir = 3;
		}
	}
	
	/**
	 * This will be affected by items.
	 */

	protected float damage;

	public float getDamage() {
		return damage;
	}

	public void setDamage(float dmg) {
		damage = dmg;
	}

	/**
	 * This will remain permanent for now.
	 */
	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float rng) {
		attackRange = rng;
	}

	public ActorLogic getLogic() {
		return logic;
	}

	public void setLogic(ActorLogic logic) {
		this.logic = logic;
	}

	/**
	 * Only used once.
	 */
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

	public void setPlayer(AbstractClasses role) {
		health = role.getHealth();
		resistance = role.getResistance();
		movementSpeed = role.getMoveSpeed();
		energy = role.getEnergy();
		damage = role.getDamage();
		attackRange = 2.0f;
		this.role = role;

		//update player statistics.
		if(entity.hasComponent(NetDataComponent.class)) {
			NetDataComponent netData = (NetDataComponent) entity.getComponent(NetDataComponent.class);

			//Update Health.
			netData.getAllData("Health").put("Health", (Float.parseFloat(netData.getAllData("Health").get("Health").toString()) + health));
			//Update Energy
			netData.getAllData("Energy").put("Energy", (Float.parseFloat(netData.getAllData("Energy").get("Energy").toString()) + energy));
			//Update Resistance
			netData.getAllData("Resistance").put("Resistance", (Float.parseFloat(netData.getAllData("Resistance").get("Resistance").toString()) + resistance));
			//Update Damage
			//netData.getAllData("Damage").put("Damage", (Float.parseFloat(netData.getAllData("Damage").get("Damage").toString()) + damage));
			//Update Movement Speed
			netData.getAllData("MovementSpeed").put("MovementSpeed", (Float.parseFloat(netData.getAllData("MovementSpeed").get("MovementSpeed").toString()) + movementSpeed));

			if(debug) { System.out.println("Entity role stats assigned"); }

		} else {
			if(debug) { System.out.println("WARNING: Entity does not have role stats assgined"); }
		}
	}

	public ActorStatus getStatus(){
		return status;
	}
}


