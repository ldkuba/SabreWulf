package engine.net.common_net.networking_messages;

/**
 * This is sent from Client to the Server
 * when the client clicks play.
 *
 * The server then responds with
 * LobbyConnectionResponseMessage to inform the
 * client if the a lobby is available
 *
 */
public class LobbyConnectionRequestMessage extends AbstractMessage {
    public LobbyConnectionRequestMessage(){
        this.name = name;
    }
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
