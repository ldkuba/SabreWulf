package game.common.actors;

import java.util.ArrayList;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.object.Outpost;

public class Caravan extends NPC {
	
	private ArrayList<Outpost> outposts;
	private float minRange;

	public Caravan(float minRange) {
		this.minRange = minRange;
		//outposts = new ArrayList<>();
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
			
		}
	
}
