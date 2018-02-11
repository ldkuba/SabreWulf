package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.common_net.PeerList;
import engine.server.core.Player;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
                server.notifyMessageListeners((AbstractMessage) ois.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                server.notifyConnectionListenersDisconnected(player);
            }

        }

    }
}
