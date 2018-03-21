package game.common.inventory;

import java.util.LinkedList;

import engine.maths.Vec3;
import game.common.items.AbstractItem;
import game.common.items.attributes.Attribute;

/**
 * Item is created with its attributes.
 *
 * @author Sabrewulf
 */

public class Item extends AbstractItem{

	/**
	 * Constructs an item.
	 *
	 * @param name
	 * @param attributes
	 * @param location
	 */
	public Item(String name, LinkedList<Attribute> attributes, Vec3 location) {
		super(name,attributes,location/*Image*/);
	}
	
}
