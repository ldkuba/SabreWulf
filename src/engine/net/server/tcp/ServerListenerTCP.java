package engine.net.server.tcp;

import engine.net.common_net.AbstractMessage;
import engine.net.server.core.Player;
import engine.net.server.GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerListenerTCP extends Thread{
    ObjectInputStream ois = null;
    Player player;
    GameServer gameServer;
    public ServerListenerTCP(Player player, GameServer gameServer) {
        this.player = player;
        this.gameServer = gameServer;
    }
    public void run(){
        try {
            ois = new ObjectInputStream(player.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!player.getSocket().isClosed()){
            try {
                gameServer.notifyMessageListeners((AbstractMessage) ois.readObject(), player);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                gameServer.notifyConnectionListenersDisconnected(player);
            }

        }

    }
}
