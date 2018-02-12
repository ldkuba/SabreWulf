package game.networking;

import engine.common_net.AbstractMessage;

public class ConnectionMessage extends AbstractMessage {
    public ConnectionMessage(){
        this.name = name;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name){

    }
}
