package game.server;

import engine.common_net.AbstractMessage;

public class ChooseCharacterMessage extends AbstractMessage {
    int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
