package game.client;

import engine.net.common_net.MessageListener;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientUDPMessageListener implements MessageListener {

    private Main app;
    private BlockingQueue<AbstractMessage> abstractMessageInbound;

    public ClientUDPMessageListener(Main app) {
        this.app = app;
        abstractMessageInbound = new LinkedBlockingQueue<>();
    }
    
    @Override
    public void receiveMessage(AbstractMessage msg, Player player) {

    }

    @Override
    public void receiveMessage(AbstractMessage msg) {

    }

    @Override
    public void addMessage(AbstractMessage message, Player player) {

    }

    @Override
    public void addMessage(AbstractMessage message) {

    }

    @Override
    public void handleMessageQueue() {

    }
}
