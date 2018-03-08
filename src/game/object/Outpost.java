package game.object;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;

public interface Outpost {
	int occupier;
	Vec3 location;
	Entity entity;
	// image of outpost
	ArrayList<Attribute> attributes;
	ArrayList<Item> items;
	int captureProgress;
	
	public Outpost(Location loc, Entity entity, ArrayList<Attribute> attributes, ArrayList<Item> items) {
		location = loc;
		this.entity = entity;
		attributes = new ArrayList<Attribute>;
		
	}
}
