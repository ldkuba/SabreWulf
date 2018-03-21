package game.common.items;

import java.util.LinkedList;

import engine.maths.Vec3;
import game.common.items.attributes.Attribute;

/**
 * All items will contain these values
 *
 */

public abstract class AbstractItem {
	
	//Image
	private String name;
	private LinkedList<Attribute> attributes;
	private Vec3 location;
	
	public AbstractItem() {
		
	}
	
	public AbstractItem(String name, LinkedList<Attribute> attri, Vec3 location /*Img*/) {
		this.name = name;
		attributes = attri;
		this.location = location;
		//image = img;
	}

	/**
	 * Get attributes that the item contains
	 *
	 * @return
	 */
	public LinkedList<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * Get name of item
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get location of the item
	 * @return
	 */
	public Vec3 getLocation() {
		return location;
	}

	/**
	 * Set location of the item
	 *
	 * @param location
	 */
	public void setLocation(Vec3 location){
		this.location = location;
	}
	//getImage
}
