package game.networking;

import engine.common_net.AbstractMessage;

public class NewLobbyPlayerMessage extends AbstractMessage{
    int slot;
    String name;

    public NewLobbyPlayerMessage(){

    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }
}
