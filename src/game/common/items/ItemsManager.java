package game.common.items;

import java.util.ArrayList;
import java.util.LinkedList;

import engine.maths.Vec3;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.*;

/**
 * Manages all the items that will be available during the game.
 * no author.
 *
 */

public class ItemsManager {

	/**
	 * Array containing all the items that can be displayed in the game.
	 */
	private ArrayList<Item> items = new ArrayList<Item>();

	/**
	 * Array containing items that are currently displayed in the game.
	 */
	private ArrayList<Item> ingameItems = new ArrayList<Item>();
	
	public ItemsManager() {
		
	}

	/**
	 * Creates an item, and adds the attributes that will affect the player
	 *
	 * @param name
	 * @param damage
	 * @param energy
	 * @param health
	 * @param moveSpeed
	 * @param resistance
	 */
	public void addItem(String name, float damage, float energy, float health, float moveSpeed, float resistance) {
		Vec3 pos = new Vec3(0.0f,0.0f,0.0f);
		
		LinkedList<Attribute> attributes = new LinkedList<Attribute>();
		
		//Add attributes to the item.
		if(notZero(damage)) {
			Damage atDamage = new Damage(damage);
			attributes.add(atDamage);
		}
		if(notZero(energy)){
			Energy atEnergy = new Energy(energy);
			attributes.add(atEnergy);
		}
		if(notZero(health)) {
			Health atHealth = new Health(health);
			attributes.add(atHealth);
		}
		if(notZero(moveSpeed)) {
			MovementSpeed atMoveSpeed = new MovementSpeed(moveSpeed);
			attributes.add(atMoveSpeed);
		}
		if(notZero(resistance)) {
			Resistance atResistance = new Resistance(resistance);
			attributes.add(atResistance);
		}

		//Create item and add item to the item manager.
		Item item = new Item(name,attributes,pos);
		items.add(item);
	}

	/**
	 * Remove item from the item manager
	 *
	 * @param item
	 */
	public void removeItem(Item item) {
		items.remove(item);
	}

	/**
	 * get the item from the item manager
	 *
	 * @param name
	 * @return
	 */
	public Item getItem(String name) {
		for(int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(name)) {
				return items.get(i);
			}
		}
		return null;	//Always check before getting an item out for the game.
	}

	/**
	 * Add item into the game
	 *
	 * @param item
	 */
	public void addToingameItems(Item item) {
		ingameItems.add(item);
	}

	/**
	 * Get item that is currently in the game
	 *
	 * @return
	 */
	public ArrayList<Item> getIngameItems() {
		return ingameItems;
	}

	/**
	 * Check if attribute is not zero to add the attribute into the item.
	 *
	 * @param n
	 * @return
	 */
	private boolean notZero(float n) {
		if(n > 0.0f) {
			return true;
		}
		return false;
	}
	
	
}
