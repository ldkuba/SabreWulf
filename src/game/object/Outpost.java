package game.object;

import java.util.ArrayList;
import java.util.Arrays;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;

public class Outpost {
	private int occupier;
	private Vec3 position;

	private Entity entity;
	// image of outpost
	private ArrayList<Attribute> attributes;
	private ArrayList<Item> items;
	private int captureProgress;
	
	public Outpost(Vec3 pos, Entity entity) {
		position = pos;
		this.entity = entity;
		this.attributes = new ArrayList<Attribute>();
		this.items = new ArrayList<Item>();
	}
	
	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}
	
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void setPosition(Vec3 pos) {
		position = pos;
	}
	public Vec3 getPosition() {
		return position;
	}
	
	public void setOccupier(int occupier) {
		this.occupier = occupier;
	}
	
	public int getOccupier() {
		return occupier;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
}
