package engine.net.common_net.networking_messages;

import engine.entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This message is sent once to all players
 * to inform them about their initial position
 * and about the position of the other entities
 */


public class GameStateMessage implements Serializable {

    public GameStateMessage(ArrayList<Entity> entities ){
        this.entities = entities;
    }

    ArrayList<Entity> entities;

    public GameStateMessage(){

    }
    public Entity getEntity(int id){
        return entities.get(id);
    }

    public void setEntities(ArrayList<Entity> entities){
        this.entities=entities;
    }

    public ArrayList<Entity> getEntities(){
        return entities;
    }
}
