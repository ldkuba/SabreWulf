package engine.common_net;

public class PeerList extends AbstractMessage{
    int noPlayers=0;
    public PeerList(int noPlayers){
        this.noPlayers=noPlayers;
    }

    public int getNoPlayers() {
        return noPlayers;
    }
}
