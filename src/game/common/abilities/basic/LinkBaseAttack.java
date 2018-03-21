package game.common.abilities.basic;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import game.common.logic.actions.Action;
import game.common.player.ActorManager;
import game.server.ingame.ServerMain;

/**
 * Base attack class for the Link character
 * @author SabreWulf
 *
 */
public class LinkBaseAttack extends Action {

	private int targetId;
	private int arrowNetId;

	/**
	 * Set source of attack and target
	 * @param targetId
	 * @param sourceId
	 */
	public LinkBaseAttack(int targetId, int sourceId)
	{
		super(sourceId);
		this.targetId = targetId;
		this.maxCooldown = 1.0f;
		this.cooldown = 0.0f;
		this.cost = 0.0f;
	}

	/**
	 * Checks if attack target is in range to attack before attacking
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
	 */
	@Override
	public void executeClient(ActorManager actorManager, Application app)
	{
		actorManager.getActor(targetId).update();
		actorManager.getActor(sourceId).getBaseAttack().setCooldown();
	}

	/**
	 * Execute attack in the server
	 */
	@Override
	public void executeServer(ActorManager actorManager, Application app)
	{
		float health = actorManager.getActor(targetId).getHealth() - 10.0f;
		actorManager.getActor(targetId).setHealth(health);
		actorManager.getActor(sourceId).getBaseAttack().setCooldown();
		
		int netId = app.getNetworkManager().getFreeId();
		
		Entity arrow = new Entity("Arrow");
		arrow.addComponent(new NetIdentityComponent(netId, app.getNetworkManager()));
		arrow.addComponent(new NetTransformComponent());
		arrow.getNetTransform().setPosition(actorManager.getActor(sourceId).getEntity().getNetTransform().getPosition());
		
		
		ServerMain.gameState.getScene().instantiate(arrow, -1.0f);
	}

	/**
	 * Verify if an attack action can occur.
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
