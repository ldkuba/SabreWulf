package engine.net.server.tcp;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.LobbyUpdateMessage;
import engine.net.server.core.Player;
import engine.net.common_net.networking_messages.QuitMessage;
import game.server.GameServer;
import sun.util.resources.be.LocaleNames_be;

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

                AbstractMessage msg = player.takeMessage();
                if(msg instanceof LobbyUpdateMessage){
                    LobbyUpdateMessage mse = (LobbyUpdateMessage) msg;
                    System.out.println(((LobbyUpdateMessage) msg).getPlayersInLobby().get(0).getName());
                }
                if(msg instanceof QuitMessage){
                    player.getSocket().close();
                    gameServer.removePlayer(player);
                    oos.close();
                }
                else {

                    oos.flush();
                    if(msg instanceof LobbyUpdateMessage){
                        LobbyUpdateMessage mse = (LobbyUpdateMessage) msg;
                        System.out.println(((LobbyUpdateMessage) msg).getPlayersInLobby().get(0).getName());
                    }
                    oos.writeObject(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
