package game.networking;

import engine.common_net.AbstractMessage;

public class LockInMessage extends AbstractMessage {

    int characterSelected;
    public LockInMessage(){
    }

    public int getCharacterSelected() {
        return characterSelected;
    }

    public void setCharacterSelected(int characterSelected) {
        this.characterSelected = characterSelected;
    }
}
