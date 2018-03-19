package engine.net.server.tcp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;

import engine.application.Timer;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.QuitMessage;
import engine.net.server.core.NetPlayer;
import game.server.GameServer;

public class ServerSenderTCP extends Thread{
    NetPlayer player;
    GameServer gameServer;
    ObjectOutputStream oos;
    public ServerSenderTCP(NetPlayer player, GameServer gameServer){
        this.player = player;
        this.gameServer = gameServer;
    }

    public void run(){
        try {
            oos = new ObjectOutputStream(player.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!player.getSocket().isClosed()){
            try {
                AbstractMessage msg = player.takeMessage();
                if (msg instanceof QuitMessage) {
                    player.getSocket().close();
                    gameServer.removePlayer(player);
                    oos.close();
                } else {
                    oos.writeObject(msg);
                    oos.reset();
                }
            } catch (SocketException se){
                try {
                    player.getSocket().close();
                    gameServer.removePlayer(player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
        }
    }
}
