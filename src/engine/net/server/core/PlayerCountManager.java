package engine.net.server.core;

import engine.net.common_net.networking_messages.PeerCountMessage;
import game.server.GameServer;

public class PlayerCountManager extends Thread {
    private GameServer server;

    public PlayerCountManager(GameServer server) {
        this.server = server;
    }

    public void run() {
        while(true) {
            try {

                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (server.getNoPlayers() > 0) {
                server.broadcastTCP(new PeerCountMessage(server.getNoPlayers()), server.players);
            }
        }
    }
}

