package game.common.logic.actions;

import engine.application.Application;
import engine.maths.Vec3;
import game.common.player.ActorManager;

/**
 * Action (Attack) that allows the player to attack an opponent.
 *
 * @author Sabrewulf
 */

public class AttackAction extends Action{

	private int targetId;

	/**
	 * Set source of attack, and target.
	 * @param targetId
	 * @param sourceId
	 */
	public AttackAction(int targetId, int sourceId)
	{
		super(sourceId);
		this.targetId = targetId;
	}

	/**
	 * Checks if player can attack target
	 *
	 * @param playerCoord
	 * @param enemyCoord
	 * @param playerRange
	 * @return
	 */
	public boolean inRange(Vec3 playerCoord, Vec3 enemyCoord, float playerRange) {
		
		float rangeX = playerCoord.getX() - enemyCoord.getX();
		float rangeY = playerCoord.getY() - enemyCoord.getY();
		
		//Make values positive.
		rangeX = Math.abs(rangeX);
		rangeY = Math.abs(rangeY);
		
		if( rangeX <= playerRange && rangeY <= playerRange) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Execute attack in client's machine
	 *
	 * @param actorManager
	 */
	@Override
	public void executeClient(ActorManager actorManager, Application app)
	{
		actorManager.getActor(targetId).update();
	}

	/**
	 * Execute attack in the server
	 *
	 * @param actorManager
	 */
	@Override
	public void executeServer(ActorManager actorManager, Application app)
	{
		float health = actorManager.getActor(targetId).getHealth() - 10.0f;
		actorManager.getActor(targetId).setHealth(health);
	}

	/**
	 * Verify if an attack action can occur.
	 *
	 * @param actorManager
	 * @return
	 */
	@Override
	public boolean verify(ActorManager actorManager)
	{
		Vec3 sourcePos = actorManager.getActor(sourceId).getEntity().getNetTransform().getPosition();
		Vec3 targetPos = actorManager.getActor(targetId).getEntity().getNetTransform().getPosition();
		
		float range = actorManager.getActor(sourceId).getAttackRange();
		
		return inRange(sourcePos, targetPos, range);
	}
	
	
}
