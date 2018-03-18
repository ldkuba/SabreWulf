package game.common.inventory;

import java.util.LinkedList;

import engine.maths.Vec3;
import game.common.items.AbstractItem;
import game.common.items.attributes.Attribute;

public class Item extends AbstractItem{
	
	public Item(String name, LinkedList<Attribute> attributes, Vec3 location) {
		super(name,attributes,location/*Image*/);
	}
	
}
