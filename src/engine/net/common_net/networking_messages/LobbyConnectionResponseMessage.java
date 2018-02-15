package engine.net.common_net.networking_messages;

/**
 * This is used to inform the server responds to
 * the client's lobby connection message.
 *
 * It can response positively or negatively,
 * depending on the availability of the lobby
 */
public class LobbyConnectionResponseMessage extends AbstractMessage {
    private boolean accepted;
    private String message;

    public LobbyConnectionResponseMessage(){
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccepted() {
        return accepted;
    }

}
