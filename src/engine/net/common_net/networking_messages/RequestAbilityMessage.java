package engine.net.common_net.networking_messages;

import game.common.logic.actions.Action;

public class RequestAbilityMessage {

    private Action ability;

    public RequestAbilityMessage(){};

    public void setAbility(Action ability) {
        this.ability = ability;
    }

    public Action getAbility() {
        return ability;
    }
}
