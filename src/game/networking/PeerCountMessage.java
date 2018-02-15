package game.networking;

import engine.common_net.AbstractMessage;

/**
 * Used for showing the player count in MenuState
 */
public class PeerCountMessage extends AbstractMessage {
    int noPlayers=0;
    public PeerCountMessage(int noPlayers){
        this.noPlayers=noPlayers;
    }

    public int getNoPlayers() {
        return noPlayers;
    }
}
