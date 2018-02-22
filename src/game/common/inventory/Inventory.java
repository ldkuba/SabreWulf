package game.common.inventory;

import java.util.ArrayList;

public class Inventory {
	
	private int MAX_INVENTORY = 6;
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	public Inventory(ArrayList<Item> items) {
		this.items = items;
	}
	
	public void rmvItem(Item item) {
		items.remove(item);
	}
	
	public boolean addItem(Item item) {
		if(items.size() == MAX_INVENTORY) {
			return false;
		}
		items.add(item);
		return true;
	}
	
}
