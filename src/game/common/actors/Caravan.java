package game.common.actors;

/**
 * Class of Caravan that will go across the map and give points in territories
 *
 * @author Sabrewulf
 */

import java.util.ArrayList;

import engine.application.Application;
import game.object.Outpost;


public class Caravan extends NPC {

	//Path to follow
	private ArrayList<Outpost> outposts;

	/**
	 * Sets up the Caravan
	 * @param app
	 * @param networkId
	 */
	public Caravan(Application app, int networkId) {
		super(app, networkId);
		outposts = new ArrayList<Outpost>();

		//Caravan can not be attacked.
		entity.removeTag("Targetable");
		entity.addTag("Untargetable");
	}

	/**
	 * Add outpost to the path the caravan has to follow
	 * @param outpost
	 */
	public void addOutpost(Outpost outpost) {
		outposts.add(outpost);
	}

}
