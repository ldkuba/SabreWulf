package engine.net.server.tcp;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.NetPlayer;
import game.server.GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerReceiverTCP extends Thread{
    ObjectInputStream ois = null;
    NetPlayer player;
    GameServer gameServer;
    public ServerReceiverTCP(NetPlayer player, GameServer gameServer) {
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
                gameServer.addMessage((AbstractMessage) ois.readObject(), player);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                gameServer.addConnectionEvent(player, false);
            }
        }
    }
}
