package engine.server.tcp;

import engine.common_net.AbstractMessage;
import game.networking.PeerList;
import engine.server.core.Player;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerSenderTCP extends Thread{
    Player player;
    Server server;
    ObjectOutputStream oos;
    public ServerSenderTCP(Player player, Server server){
        this.player = player;
        this.server = server;
    }

    public void run(){
        try {
            oos = new ObjectOutputStream(player.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!player.getSocket().isClosed()){
            try {
                sleep(1);
                oos.writeObject(player.takeMessage());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
