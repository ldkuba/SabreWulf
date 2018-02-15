package game.networking;

import engine.common_net.AbstractMessage;

public class LobbyConnectionMessage extends AbstractMessage {
    public LobbyConnectionMessage(){
        this.name = name;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
