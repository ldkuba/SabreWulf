package game.common.inventory;

import java.util.ArrayList;

/**
 * Contains all the items that a player will be able to have.
 * Affects the players statistics.
 *
 * @author Sabrewulf
 */

public class Inventory {


	private int MAX_INVENTORY = 6;

	//List of items that are currently in the inventory
	ArrayList<Item> items = new ArrayList<Item>();

	public Inventory(ArrayList<Item> items) {
		this.items = items;
	}

	/**
	 * Removes item from inventory
	 *
	 * @param item
	 */
	public void rmvItem(Item item) {
		items.remove(item);
	}

	/**
	 * Add item if inventory is not full
	 *
	 * @param item
	 * @return
	 */
	public boolean addItem(Item item) {
		if (items.size() == MAX_INVENTORY) {
			return false;
		}
		items.add(item);
		return true;
	}

	/**
	 * Clears inventory of items
	 *
	 */
	public void clear() {
		for (Item item : items) {
			items.remove(item);
		}

	}

}
