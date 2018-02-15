package game.networking;

import engine.common_net.AbstractMessage;
import engine.maths.PosXY;

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
