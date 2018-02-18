package engine.net.server.core;

import java.io.Serializable;

public class PlayerPayload implements Serializable {
    private String name;
    private boolean isReady;
    private int characterSelection;

    public PlayerPayload(){
        this.characterSelection = -1;
        this.isReady = false;
        this.name = "temp";
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCharacterSelection(int selection)
    {
        characterSelection = selection;
    }

    public int getCharacterSelection()
    {
        return characterSelection;
    }

    public void setReady(boolean ready){
        isReady=ready;
    }

    public boolean getReady(){
        return isReady;
    }
}
