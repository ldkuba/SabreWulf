package engine.net.common_net.networking_messages;

import engine.net.common_net.ConnectionListener;
import engine.net.server.core.NetPlayer;

public class ConnectionEvent {
    public NetPlayer player;
    public boolean connected;

    public ConnectionEvent(NetPlayer player, boolean connected){
        this.player=player;
        this.connected=connected;
    }
}
