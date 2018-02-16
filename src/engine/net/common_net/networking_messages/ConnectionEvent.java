package engine.net.common_net.networking_messages;

import engine.net.common_net.ConnectionListener;
import engine.net.server.core.Player;

public class ConnectionEvent {
    public Player player;
    public boolean connected;

    public ConnectionEvent(Player player, boolean connected){
        this.player=player;
        this.connected=connected;
    }
}
