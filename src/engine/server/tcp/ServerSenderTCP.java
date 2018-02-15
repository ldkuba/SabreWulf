package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;
import engine.server.core.QuitMessage;
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

                AbstractMessage msg = player.takeMessage();
                if(msg instanceof QuitMessage){
                    player.getSocket().close();
                    server.removePlayer(player);
                    oos.close();
                }
                else {
                    oos.writeObject(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
