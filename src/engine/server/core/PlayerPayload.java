package engine.server.core;

import java.io.Serializable;

public class PlayerPayload implements Serializable {
    private String name;
    private boolean isReady=false;
    private int characterSelection = -1;

    public PlayerPayload(){}

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
