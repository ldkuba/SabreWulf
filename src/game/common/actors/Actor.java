package game.common.actors;


import java.util.ArrayList;
import java.util.LinkedList;

import engine.AI.Navmesh;
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

public class Actor
{
	private int id;
	
	private boolean debug = true;

	private int MOVE_ANIMATION_LENGTH;
	private int MOVE_ANIMATION_RIGHT;
	private int MOVE_ANIMATION_UP;
	private int MOVE_ANIMATION_LEFT;
	private int MOVE_ANIMATION_DOWN;
	
	//-1 = stop, 0 = up, 1 = left, 2 = down, 3 = right
	private int movingDir = -1;
	
	private final float MIN_DISTANCE = 0.2f;
	private ArrayList<Vec3> currentPath;
	
	protected int team;
	protected Inventory inventory;
	protected Entity entity;
	
	protected NetDataComponent netData;
	
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
	
	public Actor(int netId, Application app)
	{
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
		
		netSprite = new NetSpriteAnimationComponent(0, 0, 2); //2 is speed
		
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
		updateNetData();
		
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
		updateNetData();
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
		updateNetData();
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
		updateNetData();
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
	
	public int getActorId()
	{
		return this.id;
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
	
	public void clearPath()
	{
		currentPath = new ArrayList<>();
	}

	/**
	 * team can be 1, 2, 3 team 1 is composed of three players (first three inTc
	 * lobby) team 2 is composed of the other three players (last three in
	 * lobby) team 3 is composed of neutral npcs (shops, cart etc)
	 */
	public int getTeam() {
		return team;
	}

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

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public float getResistance() {
		return resistance;
	}

	public void setResistance(float resistance) {
		this.resistance = resistance;
	}
	
	public float getDamage() {
		return damage;
	}

	public void setDamage(float dmg) {
		damage = dmg;
	}
	
	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float rng) {
		attackRange = rng;
	}
	
	public float getHealthRegen()
	{
		return healthRegen;
	}
	
	public void setHealthRegen(float healthRegen)
	{
		this.healthRegen = healthRegen;
	}
	
	public float getEnergyRegen()
	{
		return energyRegen;
	}
	
	public void setEnergyRegen(float energyRegen)
	{
		this.energyRegen = energyRegen;
	}

	private void updateNetData()
	{
		if(app.isHeadless())
		{
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
		}else
		{
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
	
	public void update()
	{
		updateNetData();
		
		if(!app.isHeadless())
		{
			status.update();
		}else
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
		}		
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

	public void setTeam(int team) {
		this.team = team;
	}

	public void setRole(AbstractClass role) {
		this.role = role;

		if(!app.isHeadless())
		{
			this.init(role.getResourcePath());
		}
		
		setStatistics();
		//update player statistics.
		updateNetData();
		if(debug) { System.out.println("Entity role stats assigned"); }

		 else {
			if(debug) { System.out.println("WARNING: Entity does not have role stats assgined"); }
		}
	}

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
	}

	public ActorStatus getStatus(){
		return status;
	}
}


