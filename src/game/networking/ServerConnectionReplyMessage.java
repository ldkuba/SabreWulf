package game.networking;

import engine.common_net.AbstractMessage;

public class ServerConnectionReplyMessage extends AbstractMessage {
    private boolean accepted;
    private String message;
    private int slot;

    public ServerConnectionReplyMessage(){

    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
