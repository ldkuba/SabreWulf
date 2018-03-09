package engine.net.common_net.networking_messages;

import engine.entity.NetworkEntity;

public class EntityUpdateMessage {
    private NetworkEntity entity;

    public EntityUpdateMessage(NetworkEntity entity){
        this.entity = entity;
    }

    public NetworkEntity getEntity() {
        return entity;
    }

}
