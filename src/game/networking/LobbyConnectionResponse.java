package game.networking;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;

import java.util.HashMap;

public class LobbyConnectionResponse extends AbstractMessage {
    private boolean accepted;
    private String message;

    public LobbyConnectionResponse(){
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccepted() {
        return accepted;
    }

}
