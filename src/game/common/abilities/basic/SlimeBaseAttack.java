package game.common.abilities.basic;

import engine.application.Application;
import engine.maths.Vec3;
import game.common.logic.actions.Action;
import game.common.player.ActorManager;

public class SlimeBaseAttack extends Action {

	private int targetId;

	/**
	 * Base attack class for the Slime monster
	 * @author SabreWulf
	 *
	 */
	public SlimeBaseAttack(int targetId, int sourceId) {
		super(sourceId);
		this.targetId = targetId;
		this.cooldown = 0.0f;
		this.maxCooldown = 1.0f;
		this.cost = 0f;
	}

	/**
	 * Checks if attack target is in range before attacking
	 * @param mobCoord
	 * @param enemyCoord
	 * @param mobRange
	 * @return
	 */
	public boolean inRange(Vec3 mobCoord, Vec3 enemyCoord, float mobRange) {

		float rangeX = mobCoord.getX() - enemyCoord.getX();
		float rangeY = mobCoord.getY() - enemyCoord.getY();

		// Make values positive.
		rangeX = Math.abs(rangeX);
		rangeY = Math.abs(rangeY);

		if (rangeX <= mobRange && rangeY <= mobRange) {
			return true;
		} else {
			return false;
		}
	}

	/**
     * Execute attack in client's machine
     */
	@Override
	public void executeClient(ActorManager actorManager, Application app) {
		actorManager.getActor(targetId).update();
	}

	/**
     * Execute attack in the server
     */
	@Override
	public void executeServer(ActorManager actorManager, Application app) {
		float health = actorManager.getActor(targetId).getHealth() - 10.0f;
		actorManager.getActor(targetId).setHealth(health);
	}

	/**
     * Verify if an attack action can occur
     */
	@Override
	public boolean verify(ActorManager actorManager) {
		Vec3 sourcePos = actorManager.getActor(sourceId).getEntity().getNetTransform().getPosition();
		Vec3 targetPos = actorManager.getActor(targetId).getEntity().getNetTransform().getPosition();

		float range = actorManager.getActor(sourceId).getAttackRange();

		return inRange(sourcePos, targetPos, range);
	}
}
