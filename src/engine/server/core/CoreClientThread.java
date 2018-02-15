package engine.server.core;

import engine.server.tcp.ServerListenerTCP;
import engine.server.tcp.ServerSenderTCP;
import game.server.GameServer;

public class CoreClientThread extends Thread {
    Player player;
    ServerSenderTCP CSTTCP=null;
    ServerListenerTCP CLTTCP = null;
    GameServer gameServer;

    CoreClientThread(Player player, GameServer gameServer){
        this.player=player;
        this.gameServer = gameServer;
    }

    public void run(){
        CSTTCP = new ServerSenderTCP(player, gameServer);
        CSTTCP.setName("player."+player.getSocket().getInetAddress()+".sender");
        CSTTCP.start();

        CLTTCP = new ServerListenerTCP(player, gameServer);
        CLTTCP.setName("player."+player.getSocket().getInetAddress()+".listener");
        CLTTCP.start();
    }
}
