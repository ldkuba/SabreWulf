package engine.net.common_net.networking_messages;

import engine.net.server.core.PlayerPayload;

import java.util.ArrayList;

/**
 * This is used to inform the client about Lobby Updates
 * For example, if a player locks in, this message is used.
 *
 * This message will contain redundant data every time,
 * but it will not influence the performance of the game,
 * because LobbyState is a pre-game state and this message
 * type is used only there.
 */
public class LobbyUpdateMessage extends AbstractMessage {

    private ArrayList<PlayerPayload> playersInLobby;
    private int test;

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }


    public void setPlayersInLobby(ArrayList<PlayerPayload> playersInLobby) {
        this.playersInLobby = playersInLobby;
    }

    public ArrayList<PlayerPayload> getPlayersInLobby() {
        return playersInLobby;
    }
}
