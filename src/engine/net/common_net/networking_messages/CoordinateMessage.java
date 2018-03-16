package engine.net.common_net.networking_messages;

import engine.maths.Vec3;

public class CoordinateMessage extends AbstractMessage{

    Vec3 coordinates;

    public CoordinateMessage(Vec3 coords) {
        coordinates = coords;
    }

    public Vec3 getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Vec3 coords) {
        coordinates = coords;
    }

}
