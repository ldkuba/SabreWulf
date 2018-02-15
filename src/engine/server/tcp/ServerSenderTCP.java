package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.server.core.Player;
import game.networking.QuitMessage;
import game.server.GameServer;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerSenderTCP extends Thread{
    Player player;
    GameServer gameServer;
    ObjectOutputStream oos;
    public ServerSenderTCP(Player player, GameServer gameServer){
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
                sleep(1);

                AbstractMessage msg = player.takeMessage();
                if(msg instanceof QuitMessage){
                    player.getSocket().close();
                    gameServer.removePlayer(player);
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
