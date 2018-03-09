package game.common.items;

import java.util.ArrayList;
import java.util.LinkedList;

import game.common.inventory.Item;
import game.common.items.attributes.Attribute;
import game.common.items.attributes.main.*;

/**
 * Manages all the items that will be available during the game.
 * no author.
 *
 */

public class ItemsManager {

	private ArrayList<Item> items = new ArrayList<Item>();
	
	public ItemsManager() {
		
	}
	
	public void addItem(String name, float damage, float energy, float health, float moveSpeed, float resistance) {
		
		LinkedList<Attribute> attributes = new LinkedList<Attribute>();
		
		//Improve efficieny when adding up attributes for the player.
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
		
		Item item = new Item(name,attributes);
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public Item getItem(String name) {
		for(int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(name)) {
				return items.get(i);
			}
		}
		return null;	//Always check before getting an item out for the game.
	}
	
	private boolean notZero(float n) {
		if(n > 0.0f) {
			return true;
		}
		return false;
	}
	
	
}
