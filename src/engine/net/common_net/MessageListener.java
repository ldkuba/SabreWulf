package engine.net.common_net;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;

public interface MessageListener {
    public void receiveMessage(AbstractMessage msg, Player player);
    public void receiveMessage(AbstractMessage msg);

    public void addMessage(AbstractMessage message, Player player);
    public void addMessage(AbstractMessage message);

    public void handleMessageQueue();
}
