package engine.common_net;

import java.io.Serializable;

public class XYPack implements Serializable{
    int playerId,angle;
    float x,y;

    XYPack(int playerId, int angle, float x, float y){
        this.playerId = playerId;
        this.angle = angle;
        this.x = x;
        this.y = y;
    }

}
