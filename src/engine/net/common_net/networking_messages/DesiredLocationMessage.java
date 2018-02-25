package engine.net.common_net.networking_messages;

import engine.maths.Vec2;

/**
 * This is message is sent when the client wants
 * to move from one place to another via right click
 *
 * This message is used in GameState only
 */

public class DesiredLocationMessage extends AbstractMessage {

    private Vec2 pos;
    public DesiredLocationMessage(){

    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }

    public Vec2 getPos() {
        return pos;
    }
}
