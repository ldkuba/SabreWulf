package game.common.abilities.basic;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.entity.component.SpriteComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import game.client.Main;
import game.common.customComponent.HomingComponent;
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
		this.maxCooldown = 0.7f;
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

		
		if(arrowNetId == 0)
		{
			System.out.println("WRONG");
		}
		
		Entity arrow = new Entity("Arrow");
		arrow.addComponent(new NetIdentityComponent(arrowNetId, app.getNetworkManager()));
		arrow.addComponent(new NetTransformComponent());
		arrow.getNetTransform().setPosition(actorManager.getActor(sourceId).getEntity().getNetTransform().getPosition());
		arrow.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), app.getAssetManager().getTexture("res/actors/link/abilities/basicattack.png"), 2.0f, 1.0f));
		
		Main.gameState.getScene().addEntity(arrow);
		
		System.out.println("Arrow Created: " + arrowNetId);
	}

	/**
	 * Execute attack in the server
	 */
	@Override
	public void executeServer(ActorManager actorManager, Application app)
	{
//		float health = actorManager.getActor(targetId).getHealth() - 10.0f;
//		actorManager.getActor(targetId).setHealth(health);
		
		
		actorManager.getActor(sourceId).getBaseAttack().setCooldown();
		
		arrowNetId = app.getNetworkManager().getFreeId();
		
		Entity arrow = new Entity("Arrow");
		arrow.addComponent(new NetIdentityComponent(arrowNetId, app.getNetworkManager()));
		arrow.addComponent(new NetTransformComponent());
		arrow.getNetTransform().setPosition(actorManager.getActor(sourceId).getEntity().getNetTransform().getPosition());
		arrow.addComponent(new HomingComponent(app, arrow, actorManager.getActor(targetId), 0.04f)
		{
			public void onHit()
			{
				float health = actorManager.getActor(targetId).getHealth() - 10.0f;
				actorManager.getActor(targetId).setHealth(health);
			}
		});
		
		ServerMain.gameState.getScene().addEntity(arrow);
		
		System.out.println("Arrow Created: " + arrowNetId);
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
