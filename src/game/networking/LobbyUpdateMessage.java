package game.networking;

import engine.common_net.AbstractMessage;
import engine.server.core.PlayerPayload;

import java.util.ArrayList;


public class LobbyUpdateMessage extends AbstractMessage {

    ArrayList<PlayerPayload> playersInLobby;

    public void setPlayersInLobby(ArrayList<PlayerPayload> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public ArrayList<PlayerPayload> getPlayersInLobby() {
        return playersInLobby;
    }
}
