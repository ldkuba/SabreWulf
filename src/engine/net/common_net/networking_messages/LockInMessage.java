package engine.net.common_net.networking_messages;

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
