
package engine.net.common_net.networking_messages;
import engine.net.common_net.networking_messages.AbstractMessage;

public class AttackPlayerMessage extends AbstractMessage {

    private int playerID;

    public AttackPlayerMessage() {

    }

    public void setPlayerID(int id) {
        playerID = id;
    }

    public int getPlayerID() {
        return playerID;
    }
}

