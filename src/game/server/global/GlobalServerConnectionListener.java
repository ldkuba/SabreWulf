package game.server.global;

import engine.net.common_net.ConnectionListener;
import engine.net.common_net.networking_messages.ConnectionEvent;
import engine.net.common_net.networking_messages.QuitMessage;
import engine.net.server.core.Player;
import game.server.GameServer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalServerConnectionListener implements ConnectionListener {
    CopyOnWriteArrayList<ConnectionEvent> connEvents;
    GameServer gameServer;
    private class ConnectionEvent{
        public boolean connected;
        public Player player;
        public ConnectionEvent(boolean connected, Player player){
            this.connected = connected;
            this.player = player;
        }
    }

    public GlobalServerConnectionListener(GameServer server){
        connEvents = new CopyOnWriteArrayList<>();
        this.gameServer = server;
    }

    @Override
    public void clientConnected() {

    }

    @Override
    public void clientConnected(Player player) {

    }

    @Override
    public void clientDisconnected() {

    }

    @Override
    public void clientDisconnected(Player player) {
    	player.addMsg(new QuitMessage());
    	gameServer.players.remove(player);
        try {
            player.getSocket().close();
        } catch (IOException e) {
            System.out.println("EXCEPTION PLAYER LEAVING");
        }
    }

    @Override
    public void addConnectionEvent(boolean connected) {

    }

    @Override
    public void addConnectionEvent(boolean connected, Player player) {
        connEvents.add(new ConnectionEvent(connected, player));
    }

    @Override
    public void handleConnectionQueue() {
        for(ConnectionEvent event : connEvents){
            if(event.connected){
                clientConnected(event.player);
            }
            else{
                clientDisconnected(event.player);
            }
        }
        connEvents.clear();
    }
}
