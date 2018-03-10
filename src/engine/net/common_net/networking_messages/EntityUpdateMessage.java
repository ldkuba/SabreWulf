package engine.net.common_net.networking_messages;

import engine.entity.NetworkEntity;

public class EntityUpdateMessage extends AbstractMessage
{
    private NetworkEntity entity;

    public EntityUpdateMessage() {}
    
    public EntityUpdateMessage(NetworkEntity entity){
        this.entity = entity;
    }
    
    public void setEntity(NetworkEntity entity)
    {
    	this.entity = entity;
    }

    public NetworkEntity getEntity() {
        return entity;
    }

}
