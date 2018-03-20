package engine.net.common_net.networking_messages;

import game.common.logic.actions.Action;

public class ExecuteAbilityMessage extends AbstractMessage{

    private Action ability;

    public ExecuteAbilityMessage() {

    }

    public void setAbility(Action ability) {
        this.ability = ability;
    }

    public Action getAbility() {
        return ability;
    }
}
