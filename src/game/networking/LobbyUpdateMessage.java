package game.networking;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;

import java.util.HashMap;

public class LobbyUpdateMessage extends AbstractMessage {

    private HashMap<Integer, Player> playersInLobby;

    public void setPlayersInLobby(HashMap<Integer, Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public HashMap<Integer, Player> getPlayersInLobby() {
        return playersInLobby;
    }
}
