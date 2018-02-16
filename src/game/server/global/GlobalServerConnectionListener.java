package game.server.global;

import engine.net.common_net.networking_messages.QuitMessage;
import engine.net.server.core.Player;
import game.server.GameServer;

import java.io.IOException;

public class GlobalServerConnectionListener {
    private GameServer gameServer;

    public GlobalServerConnectionListener(GameServer gameServer){
        this.gameServer = gameServer;
    }

    public void clientConnected(Player player){
        System.out.println("Connection");
    }

    public void clientDisconnected(Player player) {
        gameServer.players.remove(player);
        player.addMsg(new QuitMessage());
        try {
            player.getSocket().close();
        } catch (IOException e) {
            System.out.println("EXCEPTION PLAYER LEAVING");
        }
    }


}
