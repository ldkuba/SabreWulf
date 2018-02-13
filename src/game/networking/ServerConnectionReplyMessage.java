package game.networking;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;

import java.util.HashMap;

public class ServerConnectionReplyMessage extends AbstractMessage {
    private boolean accepted;
    private String message;
    private HashMap<Integer, Player> playersInLobby;


    public ServerConnectionReplyMessage(){

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

    public void setPlayersInLobby(HashMap<Integer, Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public HashMap<Integer, Player> getPlayersInLobby() {
        return playersInLobby;
    }
}
