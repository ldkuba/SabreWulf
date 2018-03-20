package engine.net.common_net.networking_messages;

import engine.maths.Vec3;
import game.common.logic.actions.Action;

public class RequestAbilityMessage extends AbstractMessage{

    private Action ability;
    private Vec3 targetLocation;

    public RequestAbilityMessage(){};

    public void setAbility(Action ability) {
        this.ability = ability;
    }

    public Action getAbility() {
        return ability;
    }

    public void setTargetLocation(Vec3 location) {
        targetLocation = location;
    }

    public Vec3 getTargetLocation() {
        return targetLocation;
    }
}
