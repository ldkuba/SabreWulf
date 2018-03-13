package game.common.actors;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.common.inventory.Item;
import game.common.items.attributes.Attribute;
import game.common.player.PlayerManager;
import game.object.Outpost;

public class Caravan extends NPC {
	
	private ArrayList<Outpost> outposts;
	private float minRange;
	private PlayerManager playerManager;

	public Caravan(float minRange, PlayerManager playerManager) {
		this.minRange = minRange;
		this.playerManager = playerManager;
		outposts = new ArrayList<Outpost>();
	}
	
	public void addOutpost(Outpost outpost) {
		outposts.add(outpost);
	}
	
	public boolean inRange(Actor actor) {
		float rangeX = this.getPosition().getX() - actor.getPosition().getX();
		float rangeY = this.getPosition().getY() - actor.getPosition().getY();
		
		//Make values positive.
		rangeX = toPositive(rangeX);
		rangeY = toPositive(rangeY);
		
		if (rangeX <= minRange && rangeY <= minRange) {
			return true;
		}
		else {
			return false;
		}
	
	}
	
	
	//Change coordinates to positive to properly calculate the radius of the NPC.
		private float toPositive(float coordinate) {
			return (coordinate * -1);
			
		}
		
		public void dropItems() {
			ArrayList<Player> players = new ArrayList<Player>();
			players = playerManager.getPlayers();
			for (Player player : players) {
				if (inRange(player)) {
					int playerTeam = player.getTeam();
					for (Outpost outpost : outposts) {
						if (outpost.getOccupier() == playerTeam) {
							ArrayList<Item> outpostItems = new ArrayList<Item>();
							outpostItems = outpost.getItems();
							ArrayList<Attribute> outpostAttributes = new ArrayList<Attribute>();
							outpostAttributes = outpost.getAttributes();
							for (Item item : outpostItems) {
								player.addItem(item);
							}
							for (Attribute attribute : outpostAttributes) {
								player.addAttribute(attribute);
							}
						}
					}
				}
			}
		}
	
}
