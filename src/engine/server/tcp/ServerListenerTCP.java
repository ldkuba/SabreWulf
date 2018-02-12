package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListenerTCP extends Thread{
    ObjectInputStream ois = null;
    Player player;
    Server server;
    public ServerListenerTCP(Player player, Server server) {
        this.player = player;
        this.server = server;
    }
    public void run(){
        try {
            ois = new ObjectInputStream(player.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!player.getSocket().isClosed()){
            try {
                server.notifyMessageListeners((AbstractMessage) ois.readObject(), player);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                server.notifyConnectionListenersDisconnected(player);
            }

        }

    }
}
