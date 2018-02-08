package engine.common_net;

import java.io.Serializable;

public class XY extends AbstractMessage{
    int playerId,angle;
    float x,y;

    XY(int playerId, int angle, float x, float y){
        this.playerId = playerId;
        this.angle = angle;
        this.x = x;
        this.y = y;
    }

}
