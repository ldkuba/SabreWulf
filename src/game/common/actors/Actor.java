package game.common.actors;


import java.util.ArrayList;
import java.util.LinkedList;

import engine.AI.Navmesh;
import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteAnimationComponent;
import engine.entity.component.TransformComponent;
import engine.maths.MathUtil;
import engine.maths.Vec2;
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

public class Actor
{
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
		
		netSprite = new NetSpriteAnimationComponent(0, 7, 8);
		stopMovement();
		entity.addComponent(netSprite);
		
		entity.addTag("Targetable");
		
		movementSpeed = 0.05f; //base for each actor
	}
	
	public void init(String basePath)
	{		
		if (!app.isHeadless()) {
			sprite = new SpriteAnimationComponent(app.getAssetManager().getTexture(basePath + "textures/defaultTexture.png"), MOVE_ANIMATION_LENGTH+1, 0, 0, 3.0f, 3.0f, 8);
			entity.addComponent(sprite);
		}
	}

	protected Inventory inventory;

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

	public Inventory getInventory() {
		return inventory;
	}

	public void cleanInventory() {
		inventory.clear();
	}

	protected Entity entity;

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
		
		ArrayList<Vec3> path = new ArrayList<>();//navmesh.findPath(startPos, target);
		path.add(target);

		if(path != null)
		{
			this.currentPath = path;
		}
	}

	/**
	 * team can be 1, 2, 3 team 1 is composed of three players (first three inTc
	 * lobby) team 2 is composed of the other three players (last three in
	 * lobby) team 3 is composed of neutral npcs (shops, cart etc)
	 */

	protected int team;

	public int getTeam() {
		return team;
	}

	protected Vec2 base;

	public Vec2 getBase() {
		return base;
	}

	public void setBase(Vec2 base) {
		this.base = base;
	}

	protected Vec2 position;

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	/**
	 * This will be affected by damage
	 */
	protected float HEALTH_LIMIT;
	
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
	protected float ENERGY_LIMIT;
	
	public float getEnergyLimit() {
		return ENERGY_LIMIT;
	}
	
	public void setEnergyLimit(float lim) {
		ENERGY_LIMIT = lim;
	}
	
	protected float energy;

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	/**
	 * This might be affected by items and root & snare spells
	 */
	protected float movementSpeed;

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
<<<<<<< HEAD
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

	protected float attackRange;

	public float getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(float rng) {
		attackRange = rng;
	}

	protected ActorLogic logic;

	public ActorLogic getLogic() {
		return logic;
	}
	
	/**
	 * Only used once.
	 */

	private AbstractClasses role;

	public void setPlayer(AbstractClasses role) {
		health = role.getHealth();
		resistance = role.getResistance();
		movementSpeed = role.getMoveSpeed();
		energy = role.getEnergy();
		damage = role.getDamage();
		this.role = role;
	}

}
