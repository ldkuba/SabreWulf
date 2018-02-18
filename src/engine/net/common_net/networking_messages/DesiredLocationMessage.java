package engine.net.common_net.networking_messages;

import engine.maths.PosXY;

/**
 * This is message is sent when the client wants
 * to move from one place to another via right click
 *
 * This message is used in GameState only
 */

public class DesiredLocationMessage extends AbstractMessage {

    private PosXY pos;
    public DesiredLocationMessage(){

    }

    public void setPos(PosXY pos) {
        this.pos = pos;
    }

    public PosXY getPos() {
        return pos;
    }
}
