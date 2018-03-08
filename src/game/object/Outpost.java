package game.object;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;

public class Outpost {
	private int occupier;
	private Vec3 location;
	private Entity entity;
	// image of outpost
	private ArrayList<Attribute> attributes;
	private ArrayList<Item> items;
	private int captureProgress;
	
	public Outpost(Vec3 loc, Entity entity, ArrayList<Attribute> attributes, ArrayList<Item> items) {
		location = loc;
		this.entity = entity;
		attributes = new ArrayList<Attribute>();
		
	}
}
