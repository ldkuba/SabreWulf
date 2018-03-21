package game.common.abilities.basic;

import engine.application.Application;
import engine.maths.Vec3;
import game.common.logic.actions.Action;
import game.common.player.ActorManager;

public class GhostBaseAttack extends Action {

    private int targetId;

    public GhostBaseAttack(int targetId, int sourceId)
    {
        super(sourceId);
        this.targetId = targetId;
        this.maxCooldown = 1.0f;
        this.cooldown = 0.0f;
        this.cost = 0.0f;
    }

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

    @Override
    public void executeClient(ActorManager actorManager, Application app)
    {
        actorManager.getActor(targetId).update();
    }

    @Override
    public void executeServer(ActorManager actorManager, Application app)
    {
        float health = actorManager.getActor(targetId).getHealth() - 10.0f;
        actorManager.getActor(targetId).setHealth(health);
    }

    @Override
    public boolean verify(ActorManager actorManager)
    {
        Vec3 sourcePos = actorManager.getActor(sourceId).getEntity().getNetTransform().getPosition();
        Vec3 targetPos = actorManager.getActor(targetId).getEntity().getNetTransform().getPosition();

        float range = actorManager.getActor(sourceId).getAttackRange();

        return inRange(sourcePos, targetPos, range);
    }


}
