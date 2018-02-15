package game.networking;

import engine.common_net.AbstractMessage;

/**
 * Used for informing the Server about the
 * chosen character in LobbyState
 */
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
