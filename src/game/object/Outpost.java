package game.object;

import java.util.ArrayList;

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
	
	public Outpost(Vec3 pos, Entity entity, ArrayList<Attribute> attributes, ArrayList<Item> items) {
		position = pos;
		this.entity = entity;
		attributes = new ArrayList<Attribute>;
		
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
	
}
