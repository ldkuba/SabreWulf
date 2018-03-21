package game.common.actors;

import engine.application.Application;

/**
 * Non-playable characters (NPCs) as the engine sees it
 * @author SabreWulf
 *
 */
public class NPC extends Actor{
	
	/**
	 * Creates a new NPC
	 * @param app
	 * @param networkId
	 */
	public NPC(Application app, int networkId) {
		super(networkId, app);
		
	}
	
	/**
	 * Updates the NPC
	 */
	public void update() {
		
	}
	 
}
