package game.common.items;

import java.util.LinkedList;

import engine.maths.Vec3;
import game.common.items.attributes.Attribute;

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
	
	public LinkedList<Attribute> getAttributes() {
		return attributes;
	}
	
	public String getName() {
		return name;
	}

	public Vec3 getLocation() {
		return location;
	}

	public void setLocation(Vec3 location){
		this.location = location;
	}
	//getImage
}
