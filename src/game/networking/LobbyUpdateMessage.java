package game.networking;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;

import java.util.ArrayList;


public class LobbyUpdateMessage extends AbstractMessage {

    ArrayList<Player> playersInLobby;

    public void setPlayersInLobby(ArrayList<Player> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public ArrayList<Player> getPlayersInLobby() {
        return playersInLobby;
    }
}
