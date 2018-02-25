package engine.net.common_net;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.NetPlayer;

public interface MessageListener {
    public void receiveMessage(AbstractMessage msg, NetPlayer player);
    public void receiveMessage(AbstractMessage msg);

    public void addMessage(AbstractMessage message, NetPlayer player);
    public void addMessage(AbstractMessage message);

    public void handleMessageQueue();
}
