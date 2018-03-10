package engine.net.common_net.networking_messages;

public class AttackPlayerMessage extends AbstractMessage{

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
