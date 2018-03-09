package game.common.inventory;

import java.util.LinkedList;

import game.common.items.AbstractItem;
import game.common.items.attributes.Attribute;

public class Item extends AbstractItem{
	
	public Item(String name, LinkedList<Attribute> attributes) {
		super(name,attributes/*Image*/);
	}
	
}
