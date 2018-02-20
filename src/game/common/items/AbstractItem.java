package game.common.items;

import java.util.LinkedList;

import game.common.items.attributes.Attribute;

public class AbstractItem {
	
	//Image
	private String name;
	private LinkedList<Attribute> attributes;
	
	public AbstractItem() {
		
	}
	
	public AbstractItem(String name, LinkedList<Attribute> attri /*Img*/) {
		this.name = name;
		attributes = attri;
		//image = img;
	}
	
	public LinkedList<Attribute> getAttributes() {
		return attributes;
	}
	
	public String getName() {
		return name;
	}
	
	//getImage
}
