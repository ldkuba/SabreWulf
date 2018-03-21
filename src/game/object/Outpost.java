package game.object;

import java.util.ArrayList;
import java.util.Arrays;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;

/**
 * Outposts that will be placed in the map
 * @author User
 *
 */
public class Outpost {
	private int occupier;
	private Vec3 position;

	private Entity entity;
	// image of outpost
	private ArrayList<Attribute> attributes;
	private ArrayList<Item> items;
	private int captureProgress;
	
	/**
	 * Creates an Outpost wi to be placed in a specific location on the map
	 * @param pos
	 * @param entity
	 */
	public Outpost(Vec3 pos, Entity entity) {
		position = pos;
		this.entity = entity;
		this.attributes = new ArrayList<Attribute>();
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * Adds an attribute to the array of attributes provided by the outpost
	 * @param attribute
	 */
	
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	/**
	 * Gets the array of attributes provided by the outpost
	 * @return
	 */
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}
	
	/**
	 * Adds an item to the array of items provided by the outpost
	 * @param item
	 */
	public void addItem(Item item) {
		items.add(item);
	}
	
	/**
	 * Gets the array of items provided by the outpost
	 * @return
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * Sets the position of the outpost on the map
	 * @param pos
	 */
	public void setPosition(Vec3 pos) {
		position = pos;
	}
	
	/**
	 * Gets the position of the outpost on the map
	 * @return
	 */
	public Vec3 getPosition() {
		return position;
	}
	
	/**
	 * Sets the occupier of the outpost to the team that conquers it
	 * @param occupier
	 */
	public void setOccupier(int team) {
		this.occupier = team;
	}
	
	/**
	 * Gets the team that occupies the outpost
	 * @return
	 */
	public int getOccupier() {
		return occupier;
	}
	
	/**
	 * Gets the outpost entity
	 * @return
	 */
	public Entity getEntity() {
		return entity;
	}
	
}
