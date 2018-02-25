package game.common.actors;

import engine.entity.Entity;
import engine.maths.Vec2;
import game.common.classes.AbstractClasses;
import game.common.inventory.Inventory;
import game.common.inventory.Item;
import game.common.logic.AbstractLogic;
import game.common.logic.ActorLogic;

public class Actor {

    public Actor(){

    }

    Inventory inventory;

    public void addItem(Item item){
    	inventory.addItem(item);
    }

    public void removeItem(Item item){
    	inventory.rmvItem(item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void cleanInventory(){
    	inventory.clear();
    }

    private Entity entity;

    /**
     * team can be 1, 2, 3
     * team 1 is composed of three players (first three in lobby)
     * team 2 is composed of the other three players (last three in lobby)
     * team 3 is composed of neutral npcs (shops, cart etc)
     */

    private int team;

    public int getTeam() {
        return team;
    }
    
    private Vec2 base;
    
    public Vec2 getBase() {
    	return  base;
    }
    
    public void setBase(Vec2 base) {
    	this.base = base;
    }
    
    private Vec2 position;
    
    public Vec2 getPosition() {
    	return position;
    }
    
    public void setPosition(Vec2 position) {
    	this.position = position;
    }

    /**
     *  This will be affected by damage
     */
    private float health;

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * This will be affected by casting spells
     */
    private float energy;

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    /**
     * This might be affected by items and root & snare spells
     */
    private float movementSpeed;

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * This will be affected by items
     */
    private float resistance;

    public float getResistance() {
        return resistance;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }
    
    /**
     * This will be affected by items.
     */
    
    private float damage;
    
    public float getDamage() {
    	return damage;
    }
   
    public void setDamage(float dmg) {
    	damage = dmg;
    }
    
    private float attackRange;
    
    public float getAttackRange() {
    	return attackRange;
    }
    
    public void setAttackRange(float rng) {
    	attackRange = rng;
    }
    
    private ActorLogic logic;
    
    public ActorLogic getLogic() {
    	return logic;
    }

    /**
     * Only used once.
     */
    
    private AbstractClasses role;
    
    public void setPlayer(AbstractClasses role, ActorLogic logic, Vec2 base) {
    	health = role.getHealth();
    	resistance = role.getResistance();
    	movementSpeed = role.getMoveSpeed();
    	energy = role.getEnergy();
    	damage = role.getDamage();
    	this.role = role;
    	this.logic = logic;
    	this.base = base;
    }

}
